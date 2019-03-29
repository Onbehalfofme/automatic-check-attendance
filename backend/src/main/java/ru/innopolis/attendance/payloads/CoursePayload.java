package ru.innopolis.attendance.payloads;

import lombok.Data;
import ru.innopolis.attendance.models.Course;

@Data
public class CoursePayload {

    private long id;

    private String name;

    public CoursePayload(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}
