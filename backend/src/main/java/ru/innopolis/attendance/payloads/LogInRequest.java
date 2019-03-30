package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NonNull;

@Data
public class LogInRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;
}
