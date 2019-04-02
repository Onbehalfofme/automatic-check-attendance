package ru.innopolis.attendance.models;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(UserProfile.class)
public class UserProfile_ {

    public static volatile SingularAttribute<UserProfile, Long> id;
    public static volatile SingularAttribute<UserProfile, String> email;
    public static volatile SingularAttribute<UserProfile, String> password;
    public static volatile SingularAttribute<UserProfile, Role> role;
    public static volatile SingularAttribute<UserProfile, String> name;
    public static volatile SingularAttribute<UserProfile, String> surname;
    public static volatile SingularAttribute<UserProfile, LocalDate> birthday;
    public static volatile SingularAttribute<UserProfile, Short> groupNumber;
    public static volatile CollectionAttribute<UserProfile, LessonStudent> lessonStudents;
    public static volatile CollectionAttribute<UserProfile, Course> enrolledCourses;
}
