package com.tcs.e_commerce_back_end.repository.file;

import com.tcs.e_commerce_back_end.model.entity.file.FileDB;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB,String> {
    public List<FileDB> findAllByIdIn(List<String>ids);
}
