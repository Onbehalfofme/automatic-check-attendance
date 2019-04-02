package ru.innopolis.attendance.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogInRequestDTO {

    private String email;

    private String password;
}
