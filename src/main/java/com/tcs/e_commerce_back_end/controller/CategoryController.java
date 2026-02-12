package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.model.dto.category.CategoryDto;
import com.tcs.e_commerce_back_end.model.dto.category.CategoryListDto;
import com.tcs.e_commerce_back_end.model.entity.Category;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelPagination;
import com.tcs.e_commerce_back_end.service.CategoryService;
import com.tcs.e_commerce_back_end.utils.pageable.PaginationEntityResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService service;

  public CategoryController(CategoryService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<PaginationEntityResponse<CategoryListDto>> listCategory(@RequestParam(required = false) String search, @ModelAttribute ModelPagination pagination) {
    return ResponseEntity.ok(new PaginationEntityResponse<>(service.listCategory(search,pagination)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryListDto> getCategoryById(@PathVariable long id) {
    return ResponseEntity.ok(new CategoryListDto(service.categoryFindById(id)));
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Category> createCategory(@RequestParam("name") String name,
                                                 @RequestPart(value = "logo",required = false) MultipartFile logo) {
    return ResponseEntity.ok(service.createCategory(new CategoryDto(name,logo)));
  }
  @GetMapping(value = "/image/{imageId}")
  public ResponseEntity<Resource> updateAccountProfile (@PathVariable String imageId){
    final var documentContent = service.viewImage(imageId);
    var headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, documentContent.getContentType());
    return ResponseEntity.ok().headers(headers).body(documentContent.getResource());
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Category> updateCategoryById(
      @PathVariable Long id,
      @RequestParam(value = "name",required = false) String name,
      @RequestPart(value = "logo",required = false) MultipartFile logo) {
    return ResponseEntity.ok(service.updateCategory(id, new CategoryDto(name, logo)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    service.deleteCategory(id);
    return ResponseEntity.ok().build();
  }
}
