package com.tcs.e_commerce_back_end.service.migration;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.category.CategoryNameDto;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductInit;
import com.tcs.e_commerce_back_end.model.entity.Category;
import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.model.mapper.ProductMapper;
import com.tcs.e_commerce_back_end.repository.CategoryRepository;
import com.tcs.e_commerce_back_end.repository.CompanyRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductRepository;
import com.tcs.e_commerce_back_end.service.file.FileSystemService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class MigrationService {
  private final ClassLoader classLoader;
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final FileSystemService fileSystemService;

  public MigrationService(
          CategoryRepository categoryRepository,
          ProductRepository productRepository,
          FileSystemService fileSystemService) {
    this.classLoader = Thread.currentThread().getContextClassLoader();
    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
    this.fileSystemService = fileSystemService;
  }

  public Map<String, Category> insertDefaultDataCategory() {
    this.clearCategoryFromDB();
    Set<Category> entity = new HashSet<>();
    for (CategoryNameDto categoryNameDto : this.readCategoryJson()) {
      var newEntity = new Category();
      newEntity.setName(categoryNameDto.getName());
      entity.add(newEntity);
    }
    Map<String, Category> result = new HashMap<>();
    categoryRepository
        .saveAll(entity)
        .forEach(category -> result.put(category.getName(), category));
    return result;
  }

  public void clearCategoryFromDB() {
    var categories = categoryRepository.findAll();
    for (var category : categories) {
      category.removeAllProduct();
    }
    categoryRepository.saveAll(categories);
    categoryRepository.deleteAll();
  }

  public void insertDefaultDataProduct(Map<String, Category> categoryMap) {
    productRepository.deleteAll();
    List<Product> products = new ArrayList<>();
    var listInitValue = this.readProductJson();
      for (DtoProductInit dtoProduct : listInitValue) {
          var category = categoryMap.get(dtoProduct.getCategoryName());
          if (Objects.nonNull(category)) {
              var entity = ProductMapper.dtoProductInitToEntity(dtoProduct);
//        saveImage((long) (i + 1), entity);
              entity.setCategory(category);
              products.add(entity);
          }
      }
    productRepository.saveAll(products);
  }

  public void saveImage(Long id, Product entity) {
    String extension = handleExtension(id.intValue());
    var validFileName = new StringBuilder();
    validFileName.append("product-image-");
    validFileName.append(id);
    validFileName.append(extension);
    try (InputStream inputStream =
        this.classLoader.getResourceAsStream("static/productImage/" + validFileName)) {
      var fileId =
          fileSystemService.saveInputSteam(
              inputStream, validFileName.toString(), FileDirCategory.PRODUCT_FILES);
      entity.setImageId(fileId);
    } catch (IOException e) {
      throw new ApiExceptionStatusException("Fail to load file Image", 500);
    }
  }

  private String handleExtension(int id) {
    return id == 5 ? ".png" : ".jpg";
  }

  public List<DtoProductInit> readProductJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream inputStream =
        this.classLoader.getResourceAsStream("static/product-default.json")) {
      if (inputStream == null) {
        throw new IOException("JSON file not found: static/product-default.json");
      }
      return mapper.readValue(inputStream, new TypeReference<List<DtoProductInit>>() {});
    } catch (IOException e) {
      throw new ApiExceptionStatusException("Fail to load file product-default.json", 500);
    }
  }

  public List<CategoryNameDto> readCategoryJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream inputStream =
        this.classLoader.getResourceAsStream("static/category-default.json")) {
      if (inputStream == null) {
        throw new IOException("JSON file not found: static/category-default.json");
      }
      return mapper.readValue(inputStream, new TypeReference<List<CategoryNameDto>>() {});
    } catch (IOException e) {
      throw new ApiExceptionStatusException("Fail to load file category-default.json", 400);
    }
  }
}
