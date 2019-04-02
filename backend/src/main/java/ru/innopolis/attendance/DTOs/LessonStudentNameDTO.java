package ru.innopolis.attendance.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.LessonStudent;

import java.time.LocalDateTime;

@Data
public class LessonStudentNameDTO {

    private UserDTO student;

    private AttendanceType attendance;

//    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkIn;

//    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkOut;

    public LessonStudentNameDTO(LessonStudent lessonStudent) {
        student = new UserDTO(lessonStudent.getId().getStudent());
        attendance = lessonStudent.getAttendance();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
    }

    public LessonStudentNameDTO(UserDTO student,
                                AttendanceType attendance,
                                @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime checkIn,
                                @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm") LocalDateTime checkOut) {
        this.student = student;
        this.attendance = attendance;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
