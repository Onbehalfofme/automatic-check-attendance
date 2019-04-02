package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.LessonStudent;

import java.time.LocalDateTime;

@Data
public class LessonStudentDTO {

    private Long studentId;

    private AttendanceType attendance;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkOut;

    public LessonStudentDTO(LessonStudent lessonStudent) {
        studentId = lessonStudent.getId().getStudent().getId();
        attendance = lessonStudent.getAttendance();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
    }

    public LessonStudentDTO(Long studentId,
                            AttendanceType attendance,
                            @JsonFormat(pattern = "dd.MM.yyyy HH:mm") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime checkIn,
                            @JsonFormat(pattern = "dd.MM.yyyy HH:mm") @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime checkOut) {
        this.studentId = studentId;
        this.attendance = attendance;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
