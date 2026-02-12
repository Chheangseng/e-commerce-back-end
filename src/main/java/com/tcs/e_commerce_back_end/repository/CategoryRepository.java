package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findByLogoId(String id);
    List<Category> findAllByNameContains(String name);
    List<Category> findAllByIdIn(List<Long> ids);
    @Query("SELECT c FROM Category c WHERE" +
            "(:search IS NULL OR c.name LIKE %:search%)")
    Page<Category> pageCategory(@Param("search") String search, Pageable pageable);
}
