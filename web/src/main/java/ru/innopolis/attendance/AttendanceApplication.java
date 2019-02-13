package ru.innopolis.attendance;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AttendanceApplication {
    private static String[] args;
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        AttendanceApplication.args = args;
        AttendanceApplication.context = SpringApplication.run(AttendanceApplication.class, args);
    }
}
