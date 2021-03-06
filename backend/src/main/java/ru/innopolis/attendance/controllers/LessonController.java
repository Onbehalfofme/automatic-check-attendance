package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
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
import ru.innopolis.attendance.payloads.*;
import ru.innopolis.attendance.specifications.LessonSpecifications;
import ru.tinkoff.eclair.annotation.Log;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final LessonStudentRepository lessonStudentRepository;
    private final UserRepository userRepository;

    @Autowired
    public LessonController(CourseRepository courseRepository,
                            LessonRepository lessonRepository,
                            LessonStudentRepository lessonStudentRepository,
                            UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.lessonStudentRepository = lessonStudentRepository;
        this.userRepository = userRepository;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public LessonFullInfoDTO getLesson(@AuthenticationPrincipal UserProfileDetails userDetails,
                                       @PathVariable long lessonId) {
        Optional<Lesson> lessonCheck = lessonRepository.findById(lessonId);
        if (!lessonCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found.");
        }

        Lesson lesson = lessonCheck.get();

        UserProfile user = userRepository.getById(userDetails.getId());
        if (user.notEnrolled(lesson.getCourse().getId()) &&
                !(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_DOE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return new LessonFullInfoDTO(lesson);
    }

    @Transactional
    @Log(LogLevel.INFO)
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public LessonCreationResponseDTO createLesson(@AuthenticationPrincipal UserProfileDetails userDetails,
                                                  @RequestBody LessonCreationRequestDTO lessonDTO) {
        Optional<Course> courseCheck = courseRepository.findById(lessonDTO.getCourseId());
        if (!courseCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course doesn't exist.");
        }

        Course course = courseCheck.get();
        UserProfile user = userRepository.getById(userDetails.getId());
        if (user.notEnrolled(course.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Collection<UserProfile> students = course.getParticipants().stream()
                .filter(userProfile -> userProfile.getRole() == Role.ROLE_STUDENT)
                .collect(Collectors.toList());

        Lesson lesson = new Lesson();
        lesson.setCourse(course);
        lesson.setDateTime(lessonDTO.getDateTime());
        lesson.setRoom(lessonDTO.getRoom());
        lesson.setType(lessonDTO.getType());
        lesson.setTeacher(user);
        lesson.setLessonStudents(students.stream()
                .map(student -> {
                    LessonStudent lessonStudent = new LessonStudent();
                    lessonStudent.setId(new LessonStudent.LessonStudentPK(lesson, student));
                    lessonStudent.setAttendance(AttendanceType.ABSENT);
                    return lessonStudent;
                })
                .collect(Collectors.toList()));
        Long lessonId = lessonRepository.save(lesson).getId();

        return new LessonCreationResponseDTO(lessonId,
                students.stream()
                        .map(UserDTO::new)
                        .collect(Collectors.toList()));
    }

    @Transactional
    @Log(LogLevel.INFO)
    @PatchMapping("/update/{lessonId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public LessonFullInfoDTO updateStudents(@PathVariable long lessonId,
                                            @RequestBody Collection<LessonStudentIdDTO> studentDTOs) {
        Optional<Lesson> lessonCheck = lessonRepository.findById(lessonId);
        if (!lessonCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found.");
        }

        Lesson lesson = lessonCheck.get();

        List<LessonStudent> students = new ArrayList<>(studentDTOs.size());

        for (LessonStudentIdDTO studentDTO : studentDTOs) {
            Optional<UserProfile> userCheck = userRepository.findById(studentDTO.getStudentId());
            if (!userCheck.isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User doesn't exist.");
            }

            UserProfile user = userCheck.get();

            LessonStudent lessonStudent = new LessonStudent();
            lessonStudent.setId(new LessonStudent.LessonStudentPK(lesson, user));
            lessonStudent.setAttendance(studentDTO.getAttendance());
            lessonStudent.setCheckInTime(studentDTO.getCheckIn());
            lessonStudent.setCheckOutTime(studentDTO.getCheckOut());
            students.add(lessonStudentRepository.save(lessonStudent));
        }

        lesson.setLessonStudents(students);

        lessonRepository.save(lesson);

        return new LessonFullInfoDTO(lesson);
    }

    @Log(LogLevel.INFO)
    @GetMapping("/search")
    public Collection<LessonSearchDTO> getLessons(@AuthenticationPrincipal UserProfileDetails userProfile,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime start,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime end,
                                                  @RequestParam(required = false) LessonType type,
                                                  @RequestParam(required = false) String course,
                                                  @RequestParam(required = false) String room,
                                                  @RequestParam(required = false) String teacher) {
        Specification<Lesson> specs = Specification.where(
                LessonSpecifications.getLessonAfter(start)
                        .and(LessonSpecifications.getLessonBefore(end))
                        .and(LessonSpecifications.getLessonWithType(type))
                        .and(LessonSpecifications.getLessonWithCourseName(course))
                        .and(LessonSpecifications.getLessonInRoom(room))
                        .and(
                                LessonSpecifications.getLessonWithTeacherFirstName(teacher)
                                        .or(LessonSpecifications.getLessonWithTeacherLastName(teacher))
                        )
        );
        UserProfile user = userRepository.getById(userProfile.getId());
        if (user.getRole() != Role.ROLE_ADMIN && user.getRole() != Role.ROLE_DOE) {
            specs.and(LessonSpecifications.getLessonWithinCourses(user.getEnrolledCourses()));
        }
        return lessonRepository.findAll(specs, new Sort(Sort.Direction.ASC, Lesson_.dateTime.getName())).stream()
                .map(LessonSearchDTO::new)
                .collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/daily")
    @PreAuthorize("hasRole(T(ru.innopolis.attendance.models.Role).ROLE_STUDENT.name())")
    public Collection<LessonSearchStudentDTO> getStudentsLessons(@AuthenticationPrincipal UserProfileDetails userProfile,
                                                                 @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam LocalDate date) {
        UserProfile user = userRepository.getById(userProfile.getId());
        Specification<Lesson> specs = Specification.where(
                LessonSpecifications.getLessonOnDate(date)
                        .and(LessonSpecifications.getLessonWithinCourses(user.getEnrolledCourses()))
        );

        return lessonRepository.findAll(specs, new Sort(Sort.Direction.ASC, Lesson_.dateTime.getName())).stream()
                .map(lesson -> new LessonSearchStudentDTO(lesson, user.getId()))
                .collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/weekly")
    @PreAuthorize("hasRole(T(ru.innopolis.attendance.models.Role).ROLE_STUDENT.name())")
    public List<Collection<LessonSearchStudentDTO>> getStudentsLessonsWeek(@AuthenticationPrincipal UserProfileDetails userProfile,
                                                                           @DateTimeFormat(pattern = "dd.MM.yyyy") @RequestParam LocalDate date) {
        UserProfile user = userRepository.getById(userProfile.getId());
        List<Collection<LessonSearchStudentDTO>> weekCollection = new ArrayList<>(DayOfWeek.values().length);
        for (DayOfWeek weekDay : DayOfWeek.values()) {
            Specification<Lesson> specs = Specification.where(
                    LessonSpecifications.getLessonOnDate(date.with(WeekFields.of(Locale.FRANCE).dayOfWeek(), weekDay.getValue()))
                            .and(LessonSpecifications.getLessonWithinCourses(user.getEnrolledCourses()))
            );
            weekCollection.add(lessonRepository.findAll(specs, new Sort(Sort.Direction.ASC, Lesson_.dateTime.getName())).stream()
                    .map(lesson -> new LessonSearchStudentDTO(lesson, user.getId()))
                    .collect(Collectors.toList()));
        }
        return weekCollection;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/courseAverage/{courseId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public double getAverageCourseAttendance(@PathVariable long courseId) {
        Collection<Lesson> lessons = lessonRepository.findByCourse_IdAndTypeIn(courseId, LessonType.getShared());
        return lessons.stream()
                .filter(lesson -> !lesson.getLessonStudents().isEmpty())
                .mapToDouble(lesson -> (double) lesson.getLessonStudents().stream()
                        .filter(lessonStudent -> lessonStudent.getAttendance() == AttendanceType.PRESENT)
                        .count()
                        /
                        lesson.getLessonStudents().size())
                .average().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No lessons found."));
    }

    @Log(LogLevel.INFO)
    @GetMapping("/average")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public double getAverageAttendance() {
        Collection<Lesson> lessons = lessonRepository.findByTypeIn(LessonType.getShared());
        return lessons.stream()
                .filter(lesson -> !lesson.getLessonStudents().isEmpty())
                .mapToDouble(lesson -> (double) lesson.getLessonStudents().stream()
                        .filter(lessonStudent -> lessonStudent.getAttendance() == AttendanceType.PRESENT)
                        .count()
                        /
                        lesson.getLessonStudents().size())
                .average().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No lessons found."));
    }
}
