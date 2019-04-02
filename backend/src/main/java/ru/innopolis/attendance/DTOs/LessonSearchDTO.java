package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LessonSearchDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

    public LessonSearchDTO(Lesson lesson) {
        id = lesson.getId();
        course = new CourseDTO(lesson.getCourse());
        teacher = new UserDTO(lesson.getTeacher());
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
    }
}