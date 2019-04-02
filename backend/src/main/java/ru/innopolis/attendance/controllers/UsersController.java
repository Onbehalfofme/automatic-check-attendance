package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.attendance.data.CourseRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.Course;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfileDetails;
import ru.innopolis.attendance.DTOs.UserDTO;
import ru.tinkoff.eclair.annotation.Log;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UsersController(UserRepository userRepository,
                           CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Log(LogLevel.INFO)
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public Collection<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(UserDTO::new).collect(Collectors.toList());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public UserDTO getUser(@PathVariable long id) {
        Optional<UserProfile> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        return new UserDTO(user.get());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public Collection<UserDTO> getCourseParticipants(@AuthenticationPrincipal UserProfileDetails userDetails,
                                                     @PathVariable long courseId) {
        UserProfile user = userRepository.getById(userDetails.getId());
        if (user.notEnrolled(courseId) &&
                !(user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_DOE)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found.");
        }

        return course.get().getParticipants().stream()
                .map(UserDTO::new).collect(Collectors.toList());
    }
}