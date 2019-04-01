package ru.innopolis.attendance.DTOs;

import lombok.Data;

@Data
public class LogInRequestDTO {

    private String email;

    private String password;
}
