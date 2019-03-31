package ru.innopolis.attendance.payloads;

import lombok.Data;
import ru.innopolis.attendance.models.Course;

@Data
public class CourseDTO {

    private long id;

    private String name;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}
