package ru.innopolis.attendance.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public enum LessonType {
    LECTURE,
    TUTORIAL,
    LAB;

    public static Collection<LessonType> getShared() {
        Collection<LessonType> list = new ArrayList<>(Arrays.asList(values()));
        list.remove(LAB);
        return list;
    }
}
