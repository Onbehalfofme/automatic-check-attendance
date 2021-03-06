package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LessonCreationRequestDTO {

    private Long courseId;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;
}
