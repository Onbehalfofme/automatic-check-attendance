package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;

import java.time.LocalDate;

@Data
public class UserPayload {

    @NonNull
    private long id;

    @NonNull
    private String email;

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private Role role;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    public UserPayload(UserProfile userProfile) {
        id = userProfile.getId();
        email = userProfile.getEmail();
        name = userProfile.getName();
        surname = userProfile.getSurname();
        role = userProfile.getRole();
        birthday = userProfile.getBirthday();
    }
}
