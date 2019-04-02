package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
public class LessonSearchDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
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

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

//    public LessonSearchDTO(Long id,
//                           CourseDTO course,
//                           UserDTO teacher,
//                           LessonType type,
//                           LocalDateTime dateTime,
//                           String room) {
//        this.id = id;
//        this.course = course;
//        this.teacher = teacher;
//        this.type = type;
//        this.dateTime = dateTime;
//        this.room = room;
//    }
}
