package ru.innopolis.attendance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.converters.DateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(length = 32)
    @Enumerated(value = EnumType.STRING)
    private LessonType type;

    @NotNull
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime dateTime;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "course_id")
    private Course course;

    @NotNull
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "teacher_id")
    private UserProfile teacher;

    @NotNull
    @Size(min = 3, max = 3)
    private String room;

    @OneToMany(mappedBy = "id.lesson")
    private Collection<LessonStudent> lessonStudents;
}
