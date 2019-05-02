package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFileRequest {

    private Long lessonId;

    private String message;
}
