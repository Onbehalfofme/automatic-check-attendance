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

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime checkIn;

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime checkOut;

    public LessonStudentNameDTO(LessonStudent lessonStudent) {
        student = new UserDTO(lessonStudent.getId().getStudent());
        attendance = lessonStudent.getAttendance();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
    }

    public LessonStudentNameDTO(UserDTO student,
                                AttendanceType attendance,
                                LocalDateTime checkIn,
                                LocalDateTime checkOut) {
        this.student = student;
        this.attendance = attendance;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}
