package com.tcs.e_commerce_back_end.model.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductCreate {
  private Long id;
  @NotNull(message = "productName is required")
  @NotBlank(message = "productName is required")
  private String productName;

  @NotNull(message = "product description is required")
  @NotBlank(message = "product description is required")
  private String description;

  @NotNull(message = "product description is required")
  private Float price;

  private Long categoryId;

}
