package ru.innopolis.attendance.DTOs;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFileRequest {

    private Long lessonId;

    private String message;
}
