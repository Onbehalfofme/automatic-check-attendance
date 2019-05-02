package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.innopolis.attendance.models.Course;

@Data
@NoArgsConstructor
public class CourseDTO {

    private Long id;

    private String name;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}
