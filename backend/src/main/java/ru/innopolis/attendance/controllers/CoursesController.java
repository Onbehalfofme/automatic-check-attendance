package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.attendance.data.CourseRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfileDetails;
import ru.innopolis.attendance.payloads.CoursePayload;
import ru.innopolis.attendance.payloads.UserPayload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CoursesController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CoursesController(UserRepository userRepository,
                             CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name())")
    public Collection<CoursePayload> getAll() {
        return courseRepository.findAll().stream()
                .map(CoursePayload::new).collect(Collectors.toList());
    }

    @GetMapping("/enrolled")
    public Collection<CoursePayload> getEnrolledCourses(@AuthenticationPrincipal UserProfileDetails userDetails) {
        UserProfile user = userRepository.getById(userDetails.getId());
        return user.getEnrolledCourses().stream()
                .map(CoursePayload::new).collect(Collectors.toList());
    }
}
