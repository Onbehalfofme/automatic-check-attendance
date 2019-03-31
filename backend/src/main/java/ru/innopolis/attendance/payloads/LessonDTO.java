package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class LessonDTO {

    private long id;

    private long courseId;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

    private Collection<LessonStudentDTO> students;

    public LessonDTO(Lesson lesson) {
        id = lesson.getId();
        courseId = lesson.getCourse().getId();
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
        students = lesson.getLessonStudents().stream()
                .map(LessonStudentDTO::new)
                .collect(Collectors.toList());
    }
}
