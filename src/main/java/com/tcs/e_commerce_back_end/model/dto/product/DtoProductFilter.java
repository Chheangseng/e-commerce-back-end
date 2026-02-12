package com.tcs.e_commerce_back_end.model.dto.product;

import com.tcs.e_commerce_back_end.model.dto.category.CategoryNameDto;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductFilter {
    private double priceMax;
    private List<CategoryNameDto> categories;
    private Set<String> colors;
}
