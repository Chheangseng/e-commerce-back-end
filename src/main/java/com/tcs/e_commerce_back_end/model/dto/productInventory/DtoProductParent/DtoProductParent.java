package com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductParent;


import com.tcs.e_commerce_back_end.model.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DtoProductParent {
    private Long id;

    private String productName;

    private String description;

    private Double price;

    private Double sellPrice;

    public DtoProductParent (Product entity){
        this.id = entity.getId();
        this.productName = entity.getName();
        this.description = entity.getDescription();
        this.sellPrice = entity.getSellPrice().doubleValue();
        this.price = entity.getPrice().doubleValue();
    }
}
