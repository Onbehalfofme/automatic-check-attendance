package ru.innopolis.attendance.payloads;

import lombok.Data;

@Data
public class LogInResponseDTO {

    private String token;

    public LogInResponseDTO(String token) {
        this.token = token;
    }
}
