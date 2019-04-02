package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class LessonDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

    private Collection<LessonStudentNameDTO> students;

    public LessonDTO(Lesson lesson) {
        id = lesson.getId();
        course = new CourseDTO(lesson.getCourse());
        teacher = new UserDTO(lesson.getTeacher());
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
        students = lesson.getLessonStudents().stream()
                .map(LessonStudentNameDTO::new)
                .collect(Collectors.toList());
    }

    public LessonDTO(Long id,
                     CourseDTO course,
                     UserDTO teacher,
                     LessonType type,
                     @JsonFormat(pattern = "dd.MM.yyyy HH:mm") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime dateTime,
                     String room,
                     Collection<LessonStudentNameDTO> students) {
        this.id = id;
        this.course = course;
        this.teacher = teacher;
        this.type = type;
        this.dateTime = dateTime;
        this.room = room;
        this.students = students;
    }
}
