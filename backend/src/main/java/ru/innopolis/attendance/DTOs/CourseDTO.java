package ru.innopolis.attendance.DTOs;

import lombok.Data;
import ru.innopolis.attendance.models.Course;

@Data
public class CourseDTO {

    private Long id;

    private String name;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
    }

//    public CourseDTO(Long id,
//                     String name) {
//        this.id = id;
//        this.name = name;
//    }
}
