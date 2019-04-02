package ru.innopolis.attendance.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class LessonCreationResponseDTO {

    private Long lessonId;

    private Collection<UserDTO> students;
}
