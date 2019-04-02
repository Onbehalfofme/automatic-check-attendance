package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@AllArgsConstructor
public class LessonSearchStudentDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;

    private String room;

    private AttendanceType attendance;

    public LessonSearchStudentDTO(Lesson lesson, Long studentId) throws IllegalArgumentException {
        id = lesson.getId();
        course = new CourseDTO(lesson.getCourse());
        teacher = new UserDTO(lesson.getTeacher());
        type = lesson.getType();
        dateTime = lesson.getDateTime();
        room = lesson.getRoom();
        Optional<LessonStudent> userCheck = lesson.getLessonStudents().stream()
                .filter(lessonStudent ->
                        lessonStudent.getId().getStudent().getId()
                                .equals(studentId)).findFirst();
        attendance = userCheck.map(LessonStudent::getAttendance).orElse(null);
    }
}
