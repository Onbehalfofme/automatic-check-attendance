package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
public class LessonCreationDTO {

    private long courseId;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;
}
