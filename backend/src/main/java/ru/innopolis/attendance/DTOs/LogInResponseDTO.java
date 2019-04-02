package ru.innopolis.attendance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogInResponseDTO {

    private String token;
}
