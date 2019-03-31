package ru.innopolis.attendance.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.innopolis.attendance.models.AttendanceType;
import ru.innopolis.attendance.models.LessonStudent;

import java.time.LocalDateTime;

@Data
public class LessonStudentDTO {

    private long studentId;

    private AttendanceType attendance;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime checkOut;

    LessonStudentDTO(LessonStudent lessonStudent) {
        studentId = lessonStudent.getId().getStudent().getId();
        checkIn = lessonStudent.getCheckInTime();
        checkOut = lessonStudent.getCheckOutTime();
    }
}
