package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {

    private String email;

    private String password;

    private String name;

    private String surname;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
}
