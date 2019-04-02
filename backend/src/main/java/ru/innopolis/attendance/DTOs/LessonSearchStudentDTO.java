package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.*;

import java.time.LocalDateTime;
import java.util.Optional;

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
        Optional<LessonStudent> userCheck = lesson.getLessonStudents().stream()
                .filter(lessonStudent ->
                        lessonStudent.getId().getStudent().getId()
                                .equals(studentId)).findFirst();
        attendance = userCheck.map(LessonStudent::getAttendance).orElse(null);
    }

    public LessonSearchStudentDTO(Long id,
                                  CourseDTO course,
                                  UserDTO teacher,
                                  LessonType type,
                                  LocalDateTime dateTime,
                                  String room,
                                  AttendanceType attendance) {
        this.id = id;
        this.course = course;
        this.teacher = teacher;
        this.type = type;
        this.dateTime = dateTime;
        this.room = room;
        this.attendance = attendance;
    }
}
