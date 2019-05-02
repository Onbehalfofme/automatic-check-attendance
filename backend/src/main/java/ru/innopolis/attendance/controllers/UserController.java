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
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.*;
import ru.innopolis.attendance.DTOs.UserDTO;
import ru.innopolis.attendance.specifications.UserProfileSpecifications;
import ru.tinkoff.eclair.annotation.Log;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public UserController(UserRepository userRepository,
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

    @Log(LogLevel.INFO)
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole(" +
            "T(ru.innopolis.attendance.models.Role).ROLE_ADMIN.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_DOE.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_PROFESSOR.name()," +
            "T(ru.innopolis.attendance.models.Role).ROLE_TA.name())")
    public Collection<UserDTO> getUsers(@RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdayStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthdayEnd,
                                        @RequestParam(required = false, value = "role") Collection<Role> roles,
                                        @RequestParam(required = false, value = "groupNumber") Collection<Short> groupNumbers,
                                        @RequestParam(required = false) String name) {
        Specification<UserProfile> specs = Specification.where(
                UserProfileSpecifications.getUserWithBirthdayAfter(birthdayStart)
                        .and(UserProfileSpecifications.getUserWithBirthdayBefore(birthdayEnd))
                        .and(UserProfileSpecifications.getUserWithRoles(roles))
                        .and(UserProfileSpecifications.getUserWithGroupNumbers(groupNumbers))
                        .and(
                                UserProfileSpecifications.getUserWithFirstName(name)
                                        .or(UserProfileSpecifications.getUserWithLastName(name))
                        )
        );

        return userRepository.findAll(specs, new Sort(Sort.Direction.ASC, UserProfile_.name.getName())).stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }
}