package ru.innopolis.attendance.DTOs;

import lombok.Data;

@Data
public class LogInRequestDTO {

    private String email;

    private String password;

//    public LogInRequestDTO(String email,
//                           String password) {
//        this.email = email;
//        this.password = password;
//    }
}
