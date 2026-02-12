package com.tcs.e_commerce_back_end.model.modelAttribute;

import com.tcs.e_commerce_back_end.utils.CalculatorMinMax;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelProductParam extends ModelPagination {
  private String search = "";
  private String categoryId;
  private String color;
  private int priceMin = 0;
  private int priceMax = 0;
  private int rateMin = 0;
  private int rateMax = 0;

  @JsonIgnore
  public List<Long> getListCategoriesIds() {
    if (Objects.isNull(this.categoryId)) {
      return new ArrayList<>();
    }
    var category = categoryId.split(", ");
    return Arrays.stream(category).map(Long::valueOf).toList();
  }

  @JsonIgnore
  public List<String> getListColors() {
    if (Objects.isNull(this.color)) {
      return new ArrayList<>();
    }
    var colors = color.split(", ");
    return Arrays.stream(colors).toList();
  }

  @JsonIgnore
  public CalculatorMinMax getPriceMinMax() {
    return new CalculatorMinMax(this.priceMin, this.priceMax);
  }

  @JsonIgnore
  public CalculatorMinMax getRateMinMax() {
    return new CalculatorMinMax(this.rateMin, this.rateMax);
  }
}
