package ru.innopolis.attendance.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.converters.DateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LessonStudent {

    @Data
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LessonStudentPK implements Serializable {

        @NotNull
        @ManyToOne(cascade = CascadeType.DETACH)
        @JoinColumn(name = "lesson_id")
        private Lesson lesson;

        @NotNull
        @ManyToOne(cascade = CascadeType.DETACH)
        @JoinColumn(name = "student_id")
        private UserProfile student;
    }

    @EmbeddedId
    private LessonStudentPK id;

    @NotNull
    @Column(length = 32)
    @Enumerated(value = EnumType.STRING)
    private AttendanceType attendance;

    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime checkInTime;

    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime checkOutTime;

    private String reason;
}
