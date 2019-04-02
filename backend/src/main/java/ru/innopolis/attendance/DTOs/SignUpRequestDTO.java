package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {

    private String email;

    private String password;

    private String name;

    private String surname;

//    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    public SignUpRequestDTO(String email,
                            String password,
                            String name,
                            String surname,
                            @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate birthday) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
}
