package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.attendance.data.CourseRepository;
import ru.innopolis.attendance.data.LessonRepository;
import ru.innopolis.attendance.data.LessonStudentRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.*;
import ru.innopolis.attendance.payloads.LessonPayload;
import ru.innopolis.attendance.payloads.LessonStudentPayload;
import ru.innopolis.attendance.payloads.UserPayload;
import ru.tinkoff.eclair.annotation.Log;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final UserRepository userRepository;

    @Autowired
    private LessonController(CourseRepository courseRepository,
                             LessonRepository lessonRepository,
                             LessonStudentRepository lessonStudentRepository,
                             UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.lessonStudentRepository = lessonStudentRepository;
        this.userRepository = userRepository;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name(), " +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public LessonPayload getLesson(@AuthenticationPrincipal UserProfileDetails userDetails,
                                    @PathVariable long id) {
        Optional<Lesson> lessonCheck = lessonRepository.findById(id);
        if (!lessonCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson fon found.");
        }

        Lesson lesson = lessonCheck.get();

        UserProfile user = userRepository.getById(userDetails.getId());
        if (user.notEnrolled(lesson.getCourse().getId()) &&
                !(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_DOE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new LessonPayload(lesson);
    }

    @Log(LogLevel.INFO)
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public Collection<UserPayload> createLesson(@AuthenticationPrincipal UserProfileDetails userDetails,
                                                 @RequestBody LessonPayload lessonPayload) {
        Optional<Course> courseCheck = courseRepository.findById(lessonPayload.getCourseId());
        if (!courseCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course doesn't exist.");
        }

        Course course = courseCheck.get();
        UserProfile user = userRepository.getById(userDetails.getId());
        if (user.notEnrolled(course.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setDateTime(lessonPayload.getDateTime());
        lesson.setRoom(lessonPayload.getRoom());
        lesson.setType(lessonPayload.getType());
        lessonRepository.save(lesson);

        return course.getParticipants().stream()
                .map(UserPayload::new).collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @PatchMapping("/update/{lessonId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name(), " +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public LessonPayload updateStudents(@PathVariable long lessonId,
                                        Collection<LessonStudentPayload> studentsPayload) {
        Optional<Lesson> lessonCheck = lessonRepository.findById(lessonId);
        if (!lessonCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found.");
        }

        Lesson lesson = lessonCheck.get();
        return new LessonPayload(lesson);
    }
}
