package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.attendance.models.Lesson;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findById(long id);
}
