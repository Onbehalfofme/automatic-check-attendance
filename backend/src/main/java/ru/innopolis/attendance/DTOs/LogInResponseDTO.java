package ru.innopolis.attendance.DTOs;

import lombok.Data;

@Data
public class LogInResponseDTO {

    private String token;

    public LogInResponseDTO(String token) {
        this.token = token;
    }
}
