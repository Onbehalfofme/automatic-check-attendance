package ru.innopolis.attendance.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.LessonStudent;

import java.time.LocalDateTime;

@Data
public class LessonStudentDTO {

    private Long studentId;

    private AttendanceType attendance;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime checkIn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime checkOut;

    public LessonStudentDTO(LessonStudent lessonStudent) {
        studentId = lessonStudent.getId().getStudent().getId();
        attendance = lessonStudent.getAttendance();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
    }

    public LessonStudentDTO(Long studentId,
                            AttendanceType attendance,
                            LocalDateTime checkIn,
                            LocalDateTime checkOut) {
        this.studentId = studentId;
        this.attendance = attendance;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
