package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String name;

    private String surname;

    private Role role;

    private Short group;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
