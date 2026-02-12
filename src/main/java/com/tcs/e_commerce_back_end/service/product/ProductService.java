package com.tcs.e_commerce_back_end.service.product;

import com.tcs.e_commerce_back_end.emuns.file.FileDirCategory;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.category.CategoryNameDto;
import com.tcs.e_commerce_back_end.model.dto.product.DtoProductFilter;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProduct;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductList;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductViewDetail;
import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.model.mapper.ProductMapper;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelProductParam;
import com.tcs.e_commerce_back_end.repository.CategoryRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductInventoryRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductSpecification;
import com.tcs.e_commerce_back_end.service.file.FileSystemService;
import com.tcs.e_commerce_back_end.utils.DocumentContent;
import java.util.*;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ProductService {
  private final ProductRepository productRepository;
  private final ProductInventoryRepository productInventoryRepository;
  private final CategoryRepository categoryRepository;
  private final FileSystemService fileSystemService;

  public Page<DtoProductList> getProductList(ModelProductParam productParam) {
    var products =
        productRepository.findAll(
            ProductSpecification.advanceProductFilter(productParam), productParam.toPageable());
    return products.map(DtoProductList::new);
  }

  public DtoProductFilter getAvailableFilterProduct() {
    var categories = new ArrayList<CategoryNameDto>();
    var response = new DtoProductFilter();
    categoryRepository
        .findAll()
        .forEach(
            category -> categories.add(new CategoryNameDto(category.getId(), category.getName())));
    var availableColor = productInventoryRepository.getAvailableColor();
    Set<String> dtoColors =
        new HashSet<>(ObjectUtils.defaultIfNull(availableColor, new ArrayList<>()));
    response.setCategories(categories);
    response.setColors(dtoColors);
    var highPrice = productRepository.findHighestPrice();
    response.setPriceMax(Objects.isNull(highPrice) ? 0 : Math.ceil(highPrice));
    return response;
  }

  @Transactional
  public DtoProductViewDetail createProduct(DtoProduct dtoProduct) {
    var product =
        productRepository
            .findById(ObjectUtils.defaultIfNull(dtoProduct.getId(), 0L))
            .orElse(new Product());
    return new DtoProductViewDetail(ProductMapper.dtoProductToEntity(dtoProduct, product));
  }

  public DocumentContent viewImage(String id) {
    return fileSystemService
        .viewImage(id)
        .orElseThrow(() -> new ApiExceptionStatusException("Invalid Id", 404));
  }

  public void deleteImage(long id, String imageId) {
    var product = findProductById(id);
    List<String> images = this.splitImageIds(product.getImageId());
    images.forEach(
        s -> {
          if (s.equals(imageId)) {
            images.remove(s);
          }
        });
    fileSystemService.deleteFileById(imageId);
    product.setImageId(this.joinImages(images));
    productRepository.save(product);
  }

  public List<String> updateImage(long id, List<MultipartFile> files, String keepImagesId) {
    var entityProduct = findProductById(id);
    var image = entityProduct.getImageId();
    List<String> prevImageIds = this.splitImageIds(image);
    if (Objects.nonNull(keepImagesId)) {
      Set<String> keepIds = new HashSet<>(this.splitImageIds(keepImagesId));
      prevImageIds.forEach(
          string -> {
            if (!keepIds.contains(string)) {
              prevImageIds.remove(string);
            }
          });
    }
    if (Objects.nonNull(files) && !files.isEmpty()) {
      files.forEach(
          multipartFile -> {
            var fileId =
                this.fileSystemService.saveFile(multipartFile, FileDirCategory.PRODUCT_FILES);
            prevImageIds.add(fileId);
          });
    }
    entityProduct.setImageId(prevImageIds.toString());
    var response = productRepository.save(entityProduct);
    return this.splitImageIds(response.getImageId());
  }

  public void deleteProduct(long id) {
    var product = findProductById(id);
    if (Objects.nonNull(product.getImageId())) {
      var listOfImage = Arrays.stream(product.getImageId().split(", ")).toList();
      listOfImage.forEach(fileSystemService::deleteFileById);
    }
    productRepository.deleteById(id);
  }

  public DtoProductViewDetail getProductById(long id) {
    var product = findProductById(id);
    var result = new DtoProductViewDetail(product);
    result.setCategory(
        new CategoryNameDto(product.getCategory().getId(), product.getCategory().getName()));
    return result;
  }

  public Product findProductById(long id) {
    return productRepository
        .findById(id)
        .orElseThrow(
            () -> new ApiExceptionStatusException("unable to find this product id: " + id, 400));
  }

  private List<String> splitImageIds(String images) {
    if (Objects.isNull(images)) {
      return Collections.emptyList();
    }
    return Arrays.stream(images.split(", ")).toList();
  }

  private String joinImages(List<String> ids) {
    if (Objects.nonNull(ids) && !ids.isEmpty()) {
      return String.join(", ", ids);
    }
    return null;
  }
}
