package ru.innopolis.attendance.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SignUpRequestDTO {

    private String email;

    private String password;

    private String name;

    private String surname;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

//    public SignUpRequestDTO(String email,
//                            String password,
//                            String name,
//                            String surname,
//                            LocalDate birthday) {
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.surname = surname;
//        this.birthday = birthday;
//    }
}
