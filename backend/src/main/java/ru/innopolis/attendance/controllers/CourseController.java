package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.attendance.data.CourseRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfileDetails;
import ru.innopolis.attendance.DTOs.CourseDTO;
import ru.tinkoff.eclair.annotation.Log;

import java.util.Collection;
import java.util.Collections;
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
                .map(CourseDTO::new).collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/enrolled")
    public Collection<CourseDTO> getEnrolledCourses(@AuthenticationPrincipal UserProfileDetails userDetails) {
        UserProfile user = userRepository.getById(userDetails.getId());
        return Collections.singletonList(new CourseDTO());
//        return user.getEnrolledCourses().stream()
//                .map(CourseDTO::new).collect(Collectors.toList());
    }
}
