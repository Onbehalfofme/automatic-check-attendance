package ru.innopolis.attendance.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
public class LessonCreationRequestDTO {

    private Long courseId;

    private LessonType type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

//    public LessonCreationRequestDTO(Long courseId,
//                                    LessonType type,
//                                    LocalDateTime dateTime,
//                                    String room) {
//        this.courseId = courseId;
//        this.type = type;
//        this.dateTime = dateTime;
//        this.room = room;
//    }
}
