package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SignUpRequestDTO {

    private String email;

    private String password;

    private String name;

    private String surname;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
}
