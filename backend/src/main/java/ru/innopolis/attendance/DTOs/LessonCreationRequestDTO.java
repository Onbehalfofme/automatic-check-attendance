package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LessonCreationRequestDTO {

    private Long courseId;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;
}
