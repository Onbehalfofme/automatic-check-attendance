package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
public class LessonCreationRequestDTO {

    private Long courseId;

    private LessonType type;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    private String room;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

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
