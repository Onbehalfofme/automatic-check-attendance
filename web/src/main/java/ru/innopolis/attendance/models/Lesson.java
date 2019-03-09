package ru.innopolis.attendance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "LESSON")
public class Lesson {
    @NotNull
    private Date date;

    @NotNull
    private Course course;

    @NotNull
    private User teacher;

    @NotNull
    private int room;
}
