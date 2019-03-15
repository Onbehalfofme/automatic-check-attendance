package ru.innopolis.attendance.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^[a-z]+\\.[a-z]+@innopolis\\.ru$", message = "Inappropriate email. Please, use your Innopolis email")
    private String email;

    @NotNull
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @NotNull
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull
    @Size(min = 2, message = "Your name is too short")
    private String name;

    @NotNull
    @Size(min = 2, message = "Your surname is too short")
    private String surname;

    @NotNull
    @Past(message = "Date should be valid")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @NotNull
    private boolean verified;

    public User(User user) {
        this.id = user.id;
        this.name = user.name;
        this.email = user.email;
        this.birthday = user.birthday;
        this.password = user.password;
        this.role = user.role;
        this.surname = user.surname;
        this.verified = user.verified;
    }

    @Override
    public String toString() {
        return "Email " + email + ", role " + role;
    }
}
