package com.tcs.e_commerce_back_end.model.dto.productInventory;

import com.tcs.e_commerce_back_end.model.entity.product.ProductInventory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductInventory {
    private Long id;
    private String color;
    private String size;
    private Double stock;
    @JsonIgnore
    public DtoProductInventory(ProductInventory productInventory) {
        this.id = productInventory.getId();
        this.color = productInventory.getColor();
        this.size = productInventory.getSize();
        this.stock = productInventory.getStock();
    }
}
