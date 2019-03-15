package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.attendance.models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
