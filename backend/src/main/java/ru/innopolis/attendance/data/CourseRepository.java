package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.innopolis.attendance.models.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
}
