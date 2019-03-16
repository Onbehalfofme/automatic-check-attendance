package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.attendance.models.LessonStudent;

public interface LessonStudentRepository extends JpaRepository<LessonStudent, LessonStudent.LessonStudentPK> {
}
