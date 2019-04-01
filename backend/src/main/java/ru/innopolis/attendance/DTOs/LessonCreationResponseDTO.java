package ru.innopolis.attendance.DTOs;

import lombok.Data;

import java.util.Collection;

@Data
public class LessonCreationResponseDTO {

    private Long lessonId;

    private Collection<UserDTO> students;

    public LessonCreationResponseDTO(Long lessonId, Collection<UserDTO> students) {
        this.lessonId = lessonId;
        this.students = students;
    }
}
