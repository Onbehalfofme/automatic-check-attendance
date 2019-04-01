package ru.innopolis.attendance.models;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Lesson.class)
public class Lesson_ {

    public static volatile SingularAttribute<Lesson, Long> id;
    public static volatile SingularAttribute<Lesson, LessonType> type;
    public static volatile SingularAttribute<Lesson, LocalDateTime> dateTime;
    public static volatile SingularAttribute<Lesson, Course> course;
    public static volatile SingularAttribute<Lesson, UserProfile> teacher;
    public static volatile SingularAttribute<Lesson, String> room;
    public static volatile CollectionAttribute<Lesson, LessonStudent> lessonStudents;
}
