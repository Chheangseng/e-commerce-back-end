package com.tcs.e_commerce_back_end.service.file;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.entity.file.FileDB;
import com.tcs.e_commerce_back_end.repository.file.FileDBRepository;
import com.tcs.e_commerce_back_end.utils.DocumentContent;
import com.tcs.e_commerce_back_end.utils.file.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemService {
  private final FileDBRepository repository;
  private final Path baseDirectory;

  public FileSystemService(FileDBRepository repository) {
    this.repository = repository;
    this.baseDirectory = this.getBaseDirectory();
  }

  public String saveFile(MultipartFile file, FileDirCategory category) {
    var filePath = FileUtil.saveFile(this.checkDirectoryType(category), file);
    var response =
        repository.save(new FileDB(filePath.toString(), category, file.getOriginalFilename()));
    return response.getId();
  }

  public String saveInputSteam(InputStream inputStream, String fileName, FileDirCategory category) {
    if (Objects.isNull(inputStream)) {
      throw new ApiExceptionStatusException("Input stream Not found", 500);
    }
    try {
      final var filePath = this.getBaseDirectory().resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      var response = repository.save(new FileDB(filePath.toString(), category, fileName));
      return response.getId();
    } catch (IOException e) {
      throw new ApiExceptionStatusException("Fail to Save File Input Stream", 500, e);
    }
  }

  public Optional<DocumentContent> viewImage(String id) {
    var image = repository.findById(id).orElse(null);
    if (Objects.isNull(image)) {
      return Optional.empty();
    }
    return Optional.of(FileUtil.viewImage(image.getPath()));
  }

  public void deleteFileById(String id) {
    var image = repository.findById(id).orElse(null);
    if (Objects.isNull(image)) return;
    FileUtil.deleteFile(image.getPath());
    repository.deleteById(image.getId());
  }

  private Path checkDirectoryType(FileDirCategory category) {
    try {
      Path pathDirectoryBase = this.baseDirectory.resolve(category.name());
      if (!Files.exists(pathDirectoryBase)) {
        Files.createDirectory(pathDirectoryBase.toAbsolutePath());
      }
      return pathDirectoryBase;
    } catch (IOException e) {
      throw new ApiExceptionStatusException("unable to create base directory", 400);
    }
  }

  public Path getBaseDirectory() {
    try {
      Path pathDirectoryBase = Path.of(System.getenv("BASE_PATH"), "sarana");
      if (!Files.exists(pathDirectoryBase)) {
        Files.createDirectory(pathDirectoryBase.toAbsolutePath());
      }
      return pathDirectoryBase;
    } catch (IOException e) {
      throw new ApiExceptionStatusException("unable to create base directory", 400);
    }
  }
}
