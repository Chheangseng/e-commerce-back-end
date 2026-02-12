package com.tcs.e_commerce_back_end.utils.file;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.utils.DocumentContent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    private static final DateTimeFormatter SAFE_TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS").withZone(java.time.ZoneOffset.UTC);

    public static Path saveFile(Path dir,MultipartFile file) {
        try {
            String FileName = FileUtil.generateFileName(file.getOriginalFilename());
            Path filePath = dir.resolve(FileName);
            file.transferTo(filePath.toFile());
            return filePath;
        } catch (IOException e) {
            throw new ApiExceptionStatusException("unable to save this file", 400);
        }
    }
    public static DocumentContent viewImage(Path filePath) {
        try {
            var fileManager = FileUtil.viewFile(filePath);
            var resource = new FileSystemResource(fileManager);
            var responseRequest =
                    new DocumentContent(resource, Files.probeContentType(fileManager.toPath()));
            if (Objects.isNull(responseRequest.getContentType())) {
                throw new ApiExceptionStatusException("unable to ge file type", 400);
            }
            return responseRequest;
        } catch (IOException e) {
            throw new ApiExceptionStatusException("something went wrong !!", 400);
        }
    }
    private static File viewFile(Path filePath) {
        if (!Files.exists(filePath)) {
            throw new ApiExceptionStatusException("File not exist ", 400);
        }
        return filePath.toFile();
    }
    public static void deleteFile(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ApiExceptionStatusException("unable to delete file Id: " + filePath, 400);
        }
    }
    private static String generateFileName(String originalFileName) {
        String extension = FileUtil.getFileExtension(originalFileName);
        return SAFE_TIMESTAMP_FORMATTER.format(Instant.now())
                + "_"
                + UUID.randomUUID()
                + (extension.isEmpty() ? ".png" : "." + extension);
    }
    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return ""; // No extension found
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}
