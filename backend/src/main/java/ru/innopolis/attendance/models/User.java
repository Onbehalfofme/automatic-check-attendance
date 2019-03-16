package ru.innopolis.attendance.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @Size(max = 32)
    private String email;

    @NotNull
    @Size(max = 32)
    private String password;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Role role;

    @NotNull
    @Size(max = 32)
    private String name;

    @NotNull
    @Size(max = 32)
    private String surname;

    @NotNull
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
