package com.tcs.e_commerce_back_end.service;

import com.tcs.e_commerce_back_end.emuns.NotificationType;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.notification.DtoNotification;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductViewDetail;
import com.tcs.e_commerce_back_end.model.dto.promotion.DtoPromotionCreate;
import com.tcs.e_commerce_back_end.repository.product.ProductRepository;
import java.util.*;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PromotionService {
  private final ProductRepository productRepository;
  private final NotificationService notificationService;

  public DtoProductViewDetail createPromotion(
      long productId, DtoPromotionCreate dtoPromotionCreate) {
    var findProduct =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ApiExceptionStatusException("unable to find product", 400));
    var entity = dtoPromotionCreate.mapToEntity(findProduct);
    findProduct.setPromotions(new HashSet<>(Collections.singleton(entity)));
    var response = productRepository.save(findProduct);
    var notification = new DtoNotification();
    notification.setTitle("New Promotion");
    var promotion = findProduct.getPromotions().stream().findFirst();
      promotion.ifPresent(product -> notification.setSubtitle(
              "Promotion: "
                      + findProduct.getName()
                      + " - "
                      + product.getDiscountPrice()
                      + "$"));
    notification.setNotificationType(NotificationType.PROMOTION);
    Map<String, Object> objectsMap = new HashMap<>();
    objectsMap.put("productId", findProduct.getId());
    notification.setJsonObject(objectsMap);
    notificationService.createNotification(notification);
    notificationService.pushNotification();
    return new DtoProductViewDetail(response);
  }

  public void deletePromotion(long productId) {
    var findProduct =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ApiExceptionStatusException("unable to find product", 400));
    findProduct.setPromotions(new HashSet<>());
    this.productRepository.save(findProduct);
  }
}
