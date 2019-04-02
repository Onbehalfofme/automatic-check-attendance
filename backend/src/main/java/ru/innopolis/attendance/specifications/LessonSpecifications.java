package ru.innopolis.attendance.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.innopolis.attendance.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class LessonSpecifications {

    public static Specification<Lesson> getLessonAfter(LocalDateTime dateTime) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                dateTime == null ? null :
                        criteriaBuilder.greaterThanOrEqualTo(root.get(Lesson_.dateTime), dateTime);
    }

    public static Specification<Lesson> getLessonBefore(LocalDateTime dateTime) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                dateTime == null ? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get(Lesson_.dateTime), dateTime);
    }

    public static Specification<Lesson> getLessonWithTeacher(String teacherName) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                teacherName == null ? null :
                        criteriaBuilder.like(root.get(Lesson_.teacher).get(UserProfile_.name), "%" + teacherName + "%");
    }

    public static Specification<Lesson> getLessonWithType(LessonType lessonType) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                lessonType == null ? null :
                        criteriaBuilder.equal(root.get(Lesson_.type), lessonType);
    }

    public static Specification<Lesson> getLessonWithCourseName(String courseName) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                courseName == null ? null :
                        criteriaBuilder.like(root.get(Lesson_.course).get(Course_.name), "%" + courseName + "%");
    }

    public static Specification<Lesson> getLessonInRoom(String room) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                room == null ? null :
                        criteriaBuilder.like(root.get(Lesson_.room), room + "%");
    }

    public static Specification<Lesson> getLessonWithinCourses(Collection<Course> courses) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                courses == null ? null :
                        criteriaBuilder.isTrue(root.get(Lesson_.course).in(courses));
    }

    public static Specification<Lesson> getLessonOnDate(LocalDate date) {
        return (Specification<Lesson>) (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(Lesson_.dateTime), date.atStartOfDay(), date.plusDays(1).atStartOfDay());
    }
}