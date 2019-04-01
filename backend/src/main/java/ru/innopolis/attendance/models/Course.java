package ru.innopolis.attendance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 32)
    private String name;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "COURSE_PARTICIPANTS",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private Collection<UserProfile> participants;
}
