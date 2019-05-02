package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.LessonStudent;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
class LessonStudentNameDTO {

    private UserDTO student;

    private AttendanceType attendance;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkOut;

    private String reason;

    private String reasonMessage;

    LessonStudentNameDTO(LessonStudent lessonStudent) {
        student = new UserDTO(lessonStudent.getId().getStudent());
        attendance = lessonStudent.getAttendance();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
        reason = lessonStudent.getReason();
        reasonMessage = lessonStudent.getReasonMessage();
    }
}
