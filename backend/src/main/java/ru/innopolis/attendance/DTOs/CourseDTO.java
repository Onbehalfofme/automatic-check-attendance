package ru.innopolis.attendance.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.innopolis.attendance.models.Course;

@Data
@AllArgsConstructor
public class CourseDTO {

    private Long id;

    private String name;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}
