package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.innopolis.attendance.configs.TokenAuthenticationProvider;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.DTOs.LogInRequestDTO;
import ru.innopolis.attendance.DTOs.LogInResponseDTO;
import ru.innopolis.attendance.DTOs.SignUpRequestDTO;
import ru.innopolis.attendance.DTOs.UserDTO;
import ru.tinkoff.eclair.annotation.Log;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final TokenAuthenticationProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository,
                          TokenAuthenticationProvider tokenProvider,
                          AuthenticationManager authenticationManager,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Log(LogLevel.INFO)
    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody SignUpRequestDTO user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email address is already in use.");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setEmail(user.getEmail());
        userProfile.setName(user.getName());
        userProfile.setSurname(user.getSurname());
        userProfile.setBirthday(user.getBirthday());

        userProfile.setRole(Role.ROLE_STUDENT);
        userProfile.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userProfile);

        return new UserDTO(userProfile);
    }

    @Transactional
    @Log(LogLevel.INFO)
    @PostMapping("/login")
    public LogInResponseDTO logIn(@RequestBody LogInRequestDTO user) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);

            String token = tokenProvider.createToken(auth);

            return new LogInResponseDTO(token);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password.");
        }
    }
}
