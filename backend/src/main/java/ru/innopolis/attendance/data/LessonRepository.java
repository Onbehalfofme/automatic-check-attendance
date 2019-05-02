package ru.innopolis.attendance.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.innopolis.attendance.models.Lesson;
import ru.innopolis.attendance.models.LessonType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long>, JpaSpecificationExecutor<Lesson> {

    Optional<Lesson> findById(long id);

    List<Lesson> findAll(Specification<Lesson> specification, Sort sort);

    Collection<Lesson> findByCourse_IdAndTypeIn(long id, Collection<LessonType> types);

    Collection<Lesson> findByTypeIn(Collection<LessonType> types);
}
