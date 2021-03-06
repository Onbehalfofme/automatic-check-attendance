package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.attendance.data.CourseRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfileDetails;
import ru.innopolis.attendance.payloads.CourseDTO;
import ru.tinkoff.eclair.annotation.Log;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(UserRepository userRepository,
                            CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name())")
    public Collection<CourseDTO> getAll() {
        return courseRepository.findAll().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/enrolled")
    public Collection<CourseDTO> getEnrolledCourses(@AuthenticationPrincipal UserProfileDetails userDetails) {
        UserProfile user = userRepository.getById(userDetails.getId());
        return user.getEnrolledCourses().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/enrolledFor/{teacherId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name())")
    public Collection<CourseDTO> getEnrolledCoursesFor(@PathVariable long teacherId) {
        Optional<UserProfile> check = userRepository.findById(teacherId);
        if (!check.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No teacher found.");
        }

        UserProfile user = check.get();

        return user.getEnrolledCourses().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }
}
