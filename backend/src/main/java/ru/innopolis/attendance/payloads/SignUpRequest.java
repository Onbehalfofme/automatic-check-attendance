package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class SignUpRequest {

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;
}
