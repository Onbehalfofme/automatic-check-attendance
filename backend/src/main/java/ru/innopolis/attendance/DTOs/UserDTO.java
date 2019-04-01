package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private Role role;

    private Short group;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    public UserDTO(UserProfile userProfile) {
        id = userProfile.getId();
        email = userProfile.getEmail();
        name = userProfile.getName();
        surname = userProfile.getSurname();
        role = userProfile.getRole();
        group = userProfile.getGroupNumber();
        birthday = userProfile.getBirthday();
    }
}
