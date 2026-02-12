package com.tcs.e_commerce_back_end.model.dto.category;

import com.tcs.e_commerce_back_end.model.entity.Category;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryListDto {
  private long id;
  private String name;
  private String imageId;
  private int totalProduct = 0;

  public CategoryListDto(Category category) {
    this.id = category.getId();
    this.name = category.getName();
    this.imageId = category.getLogoId();
    if (Objects.nonNull(category.getProducts())) {
      this.totalProduct = category.getProducts().size();
    } else {
      this.totalProduct = 0;
    }
  }
}
