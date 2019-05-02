package ru.innopolis.attendance.payloads;

import lombok.Data;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class LessonCreationResponseDTO {

    private Long lessonId;

    private Collection<UserDTO> students;

    public LessonCreationResponseDTO(Long lessonId,
                                     Collection<UserDTO> students) {
        this.lessonId = lessonId;
        this.students = students.stream()
                .sorted((o1, o2) -> o1.getEmail().compareToIgnoreCase(o2.getEmail()))
                .collect(Collectors.toList());
    }
}
