package ru.innopolis.attendance.models;

import lombok.Data;
import ru.innopolis.attendance.converters.DateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
public class UserProfile {

    public enum Role {
        ROLE_ADMIN,
        ROLE_DOE,
        ROLE_PROFESSOR,
        ROLE_TA,
        ROLE_STUDENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true)
    @Size(max = 32)
    private String email;

    @NotNull
    @Size(max = 32)
    private String password;

    @NotNull
    @Column(length = 32)
    @Enumerated(value = EnumType.STRING)
    private UserProfile.Role role;

    @NotNull
    @Size(max = 32)
    private String name;

    @NotNull
    @Size(max = 32)
    private String surname;

    @NotNull
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

    @OneToMany(mappedBy = "id.student")
    private Collection<LessonStudent> lessonStudents;

    public UserProfile(UserProfile userProfile) {
        this.id = userProfile.id;
        this.name = userProfile.name;
        this.email = userProfile.email;
        this.birthday = userProfile.birthday;
        this.password = userProfile.password;
        this.role = userProfile.role;
        this.surname = userProfile.surname;
        this.lessonStudents = userProfile.lessonStudents;
    }

    @Override
    public String toString() {
        return "Email " + email + ", role " + role.name();
    }
}
