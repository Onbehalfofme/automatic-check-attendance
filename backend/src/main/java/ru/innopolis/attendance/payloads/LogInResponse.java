package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NonNull;

@Data
public class LogInResponse {

    @NonNull
    private String token;
}
