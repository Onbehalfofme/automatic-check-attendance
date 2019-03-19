package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class LessonPayload {

    private long courseId;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

    private Collection<LessonStudentPayload> students;

    public LessonPayload(Lesson lesson) {
        courseId = lesson.getCourse().getId();
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
        students = lesson.getLessonStudents().stream()
                .map(LessonStudentPayload::new)
                .collect(Collectors.toList());
    }
}
