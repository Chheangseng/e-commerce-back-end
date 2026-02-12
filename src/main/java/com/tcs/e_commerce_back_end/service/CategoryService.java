package com.tcs.e_commerce_back_end.service;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.category.CategoryDto;
import com.tcs.e_commerce_back_end.model.dto.category.CategoryListDto;
import com.tcs.e_commerce_back_end.model.entity.Category;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelPagination;
import com.tcs.e_commerce_back_end.repository.CategoryRepository;
import com.tcs.e_commerce_back_end.service.file.FileSystemService;
import com.tcs.e_commerce_back_end.utils.DocumentContent;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final FileSystemService fileSystemService;

  public Page<CategoryListDto> listCategory(String search, ModelPagination pagination) {
    var page = categoryRepository.pageCategory(search, pagination.toPageable());
    return page.map(CategoryListDto::new);
  }

  public Category categoryFindById(long id) {
    return categoryRepository
        .findById(id)
        .orElseThrow(() -> new ApiExceptionStatusException("unable to find in category", 400));
  }

  public DocumentContent viewImage(String imageId) {
    return fileSystemService
        .viewImage(imageId)
        .orElseThrow(() -> new ApiExceptionStatusException("Invalid Id: " + imageId, 404));
  }

  public Category createCategory(CategoryDto categoryDto) {
    var category = new Category();
    category.setName(categoryDto.getName());
    if (Objects.nonNull(categoryDto.getFile())) {
      category.setLogoId(fileSystemService.saveFile(categoryDto.getFile(), FileDirCategory.CATEGORY_FILES));
    }
    try {
      return categoryRepository.save(category);
    } catch (DataIntegrityViolationException e) {
      throw new ApiExceptionStatusException(
          "This category name have been already used " + categoryDto.getName(), 400);
    }
  }

  public void deleteCategory(Long id) {
    var category = categoryFindById(id);
    categoryRepository.deleteById(category.getId());
  }

  public Category updateCategory(Long id, CategoryDto categoryDto) {
    var category = categoryFindById(id);
    category.setName(categoryDto.getName());
    if (Objects.nonNull(categoryDto.getFile())) {
      fileSystemService.deleteFileById(category.getLogoId());
      category.setLogoId(fileSystemService.saveFile(categoryDto.getFile(),FileDirCategory.CATEGORY_FILES));
    }
    return categoryRepository.save(category);
  }
}
