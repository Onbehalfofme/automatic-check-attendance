package ru.innopolis.attendance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogInRequestDTO {

    private String email;

    private String password;
}
