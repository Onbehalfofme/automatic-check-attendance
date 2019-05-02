package ru.innopolis.attendance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.innopolis.attendance.payloads.UploadFileRequest;
import ru.innopolis.attendance.payloads.UploadFileResponse;
import ru.innopolis.attendance.data.LessonStudentRepository;
import ru.innopolis.attendance.models.LessonStudent;
import ru.innopolis.attendance.models.UserProfileDetails;
import ru.innopolis.attendance.services.ReasonStorageService;
import ru.tinkoff.eclair.annotation.Log;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping("/reason")
public class ReasonController {

    private final ReasonStorageService reasonStorageService;
    private final LessonStudentRepository lessonStudentRepository;

    @Autowired
    public ReasonController(ReasonStorageService reasonStorageService,
                            LessonStudentRepository lessonStudentRepository) {
        this.reasonStorageService = reasonStorageService;
        this.lessonStudentRepository = lessonStudentRepository;
    }

    @Log(LogLevel.INFO)
    @PostMapping("/upload")
    public UploadFileResponse uploadReason(@AuthenticationPrincipal UserProfileDetails userDetails,
                                           @RequestParam MultipartFile file,
                                           @RequestBody UploadFileRequest request) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You have to select a file.");
        }

        Optional<LessonStudent> lessonCheck = lessonStudentRepository.findById_LessonIdAndId_StudentId(request.getLessonId(), userDetails.getId());
        if (!lessonCheck.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found.");
        }

        LessonStudent lesson = lessonCheck.get();

        if (lesson.getReason() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already have a reason, you can update it.");
        }

        String fileName;

        try {
            fileName = reasonStorageService.saveFile(file);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        lesson.setReason(fileName);
        lesson.setReasonMessage(request.getMessage());
        lessonStudentRepository.save(lesson);

        return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Log(LogLevel.INFO)
    @GetMapping("/download/{fileName}")
    public ResponseEntity downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource;
        try {
            resource = reasonStorageService.loadFile(fileName);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found.");
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong.");
        }

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Couldn't determine the file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
