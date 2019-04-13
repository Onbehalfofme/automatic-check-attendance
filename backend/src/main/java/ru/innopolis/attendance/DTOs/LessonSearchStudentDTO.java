package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.models.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
public class LessonSearchStudentDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime dateTime;

    private String room;

    private AttendanceType attendance;

    public LessonSearchStudentDTO(Lesson lesson, Long studentId) {
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
