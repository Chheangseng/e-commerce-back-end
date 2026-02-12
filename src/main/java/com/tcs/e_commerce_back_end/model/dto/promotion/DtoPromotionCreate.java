package com.tcs.e_commerce_back_end.model.dto.promotion;

import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.model.entity.product.PromotionProduct;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoPromotionCreate {
  private Long id;
  private String title;
  private String description;
  private Long startDate;
  private Long endDate;
  private Double discountPercentage;
  private Double discountPrice;

  public PromotionProduct mapToEntity(Product product) {
    var entity = new PromotionProduct();
    entity.setId(this.id);
    entity.setDescription(this.description);
    entity.setTitle(this.getTitle());
    entity.setEndDate(new Date(this.endDate));
    entity.setStartDate(new Date(this.startDate));
    entity.setDiscountPercentage(this.discountPercentage);
    entity.setDiscountPrice(this.discountPrice);
    entity.setProducts(Collections.singleton(product));
    if (Objects.isNull(this.id)) {
      product.addPromotion(entity);
    }
    return entity;
  }
}
