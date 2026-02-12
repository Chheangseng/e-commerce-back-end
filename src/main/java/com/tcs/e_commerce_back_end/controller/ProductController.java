package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.model.dto.product.DtoProductFilter;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProduct;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductList;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductViewDetail;
import com.tcs.e_commerce_back_end.model.dto.promotion.DtoPromotionCreate;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelProductParam;
import com.tcs.e_commerce_back_end.service.PromotionService;
import com.tcs.e_commerce_back_end.service.product.ProductService;
import com.tcs.e_commerce_back_end.utils.pageable.PaginationEntityResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final Logger logger = LoggerFactory.getLogger(ProductController.class);
  private final ProductService service;
  private final PromotionService promotionService;

  public ProductController(ProductService service, PromotionService promotionService) {
    this.service = service;
    this.promotionService = promotionService;
  }

  @GetMapping
  public ResponseEntity<PaginationEntityResponse<DtoProductList>> listProduct(
      @ModelAttribute ModelProductParam pagination) {
    return ResponseEntity.ok(new PaginationEntityResponse<>(service.getProductList(pagination)));
  }

  @GetMapping("/filter")
  public ResponseEntity<DtoProductFilter> filterResponseEntity() {
    return ResponseEntity.ok(service.getAvailableFilterProduct());
  }

  @PostMapping
  public ResponseEntity<DtoProductViewDetail> createProduct(
      @Valid @RequestBody DtoProduct product) {
    return ResponseEntity.ok(service.createProduct(product));
  }

  @PutMapping
  public ResponseEntity<DtoProductViewDetail> updateProduct(
      @Valid @RequestBody DtoProduct product) {
    return ResponseEntity.ok(service.createProduct(product));
  }

  @PutMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<String>> updateImage(
      @PathVariable long id,
      @RequestPart(required = false) List<MultipartFile> files,
      @RequestParam(required = false) String keepImageId) {
    return ResponseEntity.ok(service.updateImage(id, files, keepImageId));
  }

  @DeleteMapping(value = "/image/{id}")
  public ResponseEntity<Void> deleteImage(@PathVariable long id, @RequestParam String imageIds) {
    service.deleteImage(id, imageIds);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/image/{imageId}")
  public ResponseEntity<Resource> updateAccountProfile(@PathVariable String imageId) {
    final var documentContent = service.viewImage(imageId);
    var headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, documentContent.getContentType());
    return ResponseEntity.ok().headers(headers).body(documentContent.getResource());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    service.deleteProduct(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DtoProductViewDetail> viewProduct(@PathVariable Long id) {
    return ResponseEntity.ok(service.getProductById(id));
  }

  @PutMapping("/{id}/apply-promotion")
  public ResponseEntity<DtoProductViewDetail> applyPromotion(
      @PathVariable Long id, @RequestBody DtoPromotionCreate dto) {
    return ResponseEntity.ok(promotionService.createPromotion(id, dto));
  }

  @DeleteMapping("/{id}/apply-promotion")
  public ResponseEntity<Void> applyPromotion(@PathVariable Long id) {
    promotionService.deletePromotion(id);
    return ResponseEntity.ok().build();
  }
}
