package ru.innopolis.attendance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.converters.DateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Convert(converter = DateConverter.class)
    private LocalDate birthday;

    @OneToMany(mappedBy = "id.student")
    private Collection<LessonStudent> lessonStudents;

    @Override
    public String toString() {
        return "Email " + email + ", role " + role.name();
    }
}
