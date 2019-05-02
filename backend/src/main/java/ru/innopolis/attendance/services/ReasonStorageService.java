package ru.innopolis.attendance.services;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Objects;

@Service
public class ReasonStorageService {

    private final Path reasonStorageLocation;

    @Autowired
    public ReasonStorageService(@Value("${file.reasonDir}") String reasonDir) {
        this.reasonStorageLocation = Paths.get(reasonDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.reasonStorageLocation);
        } catch (IOException ex) {
            throw new SecurityException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new InvalidFileNameException(fileName, "Sorry! Filename contains invalid path sequence.");
        }

        Path targetLocation = this.reasonStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    public Resource loadFile(String fileName) throws FileNotFoundException, MalformedURLException {
        Path filePath = this.reasonStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return resource;
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }
}
