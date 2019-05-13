package ru.innopolis.attendance.controllers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.innopolis.attendance.data.LessonRepository;
import ru.innopolis.attendance.data.UserRepository;
import ru.innopolis.attendance.models.*;
import ru.innopolis.attendance.specifications.UserProfileSpecifications;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/csv")
public class CsvController {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public CsvController(LessonRepository lessonRepository,
                         UserRepository userRepository) {
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/save")
    public void saveAttendance(@RequestParam String fileName,
                               @RequestParam long courseId,
                               @RequestParam(value = "type") Collection<LessonType> types) throws IOException {
        Collection<Lesson> lessons = lessonRepository.findByCourse_IdAndTypeIn(courseId, types);
        try {
            Files.createDirectories(Paths.get("./csv").toAbsolutePath().normalize());
        } catch (IOException ex) {
            throw new SecurityException("Could not create the directory where the uploaded files will be stored.", ex);
        }
        FileWriter out = new FileWriter("./csv/" + fileName + ".csv");
        Collection<String> headers = new ArrayList<>();
        headers.add("date");
        headers.addAll(userRepository.findAll(Specification.where(
                UserProfileSpecifications.getUserWithRoles(Collections.singletonList(Role.ROLE_STUDENT))
        ), new Sort(Sort.Direction.ASC, UserProfile_.email.getName()))
                .stream().map(UserProfile::getEmail)
                .collect(Collectors.toList()));
        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])))) {
            for (Lesson lesson : lessons) {
                for (String header : headers) {
                    if (header.equals("date")) {
                        printer.print(lesson.getDateTime());
                    } else {
                        printer.print(lesson.getLessonStudents().stream()
                                .filter(lessonStudent -> lessonStudent.getId().getStudent().getEmail().equals(header))
                                .findFirst()
                                .orElse(new LessonStudent(AttendanceType.ABSENT))
                                .getAttendance());
                    }
                }
            }
        }
    }
}
