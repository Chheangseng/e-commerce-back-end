package com.tcs.e_commerce_back_end.model.entity.file;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import jakarta.persistence.*;
import java.nio.file.Path;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String Id;
    private String originalFileName;
    @Column(nullable = false)
    private String storePath;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FileDirCategory category;

    public FileDB(String storePath,FileDirCategory category ,String originalFileName) {
        this.originalFileName = originalFileName;
        this.storePath = storePath;
        this.category = category;
    }

    public Path getPath() {
        return Path.of(storePath);
    }
}
