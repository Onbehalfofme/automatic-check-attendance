package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogInRequestDTO {

    private String email;

    private String password;
}
