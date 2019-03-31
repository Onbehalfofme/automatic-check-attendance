package ru.innopolis.attendance.payloads;

import lombok.Data;

@Data
public class LogInRequestDTO {

    private String email;

    private String password;
}
