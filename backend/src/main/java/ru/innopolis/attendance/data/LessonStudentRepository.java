package ru.innopolis.attendance.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.innopolis.attendance.models.LessonStudent;

import java.util.Optional;

public interface LessonStudentRepository extends JpaRepository<LessonStudent, LessonStudent.LessonStudentPK> {

    Optional<LessonStudent> findById_LessonIdAndId_StudentId(long lessonId, long studentId);
}
