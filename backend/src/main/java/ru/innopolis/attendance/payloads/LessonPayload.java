package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NonNull;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class LessonPayload {

    @NonNull
    private long id;

    @NonNull
    private long courseId;

    @NonNull
    private LessonType type;

    @NonNull
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    @NonNull
    private String room;

    private Collection<LessonStudentPayload> students;

    public LessonPayload(Lesson lesson) {
        id = lesson.getId();
        courseId = lesson.getCourse().getId();
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
        students = lesson.getLessonStudents().stream()
                .map(LessonStudentPayload::new)
                .collect(Collectors.toList());
    }
}
