package ru.innopolis.attendance.payloads;

import lombok.Data;
import lombok.NonNull;
import ru.innopolis.attendance.models.Course;

@Data
public class CoursePayload {

    @NonNull
    private long id;

    @NonNull
    private String name;

    public CoursePayload(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }
}
