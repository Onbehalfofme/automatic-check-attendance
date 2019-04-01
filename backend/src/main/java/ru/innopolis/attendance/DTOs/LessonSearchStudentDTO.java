package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.time.LocalDateTime;

@Data
public class LessonSearchStudentDTO {

    private Long id;

    private CourseDTO course;

    private UserDTO teacher;

    private LessonType type;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
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
        attendance = lesson.getLessonStudents().stream()
                .filter(lessonStudent ->
                        lessonStudent.getId().getStudent().getId()
                                .equals(studentId)).findFirst().orElseThrow(IllegalArgumentException::new).getAttendance();
    }
}
