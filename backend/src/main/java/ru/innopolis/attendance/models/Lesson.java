package ru.innopolis.attendance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "LESSON")
public class Lesson {
    @NotNull
    private LocalDateTime date;

    @NotNull
    private Course course;

    @NotNull
    private User teacher;

    @NotNull
    @Size(min = 3, max = 3)
    private String room;
}
