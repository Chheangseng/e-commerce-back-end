package com.tcs.e_commerce_back_end.model.dto.order.parent;

import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrderItemParent {
    private long id;
    private long productId;
    private String name;
    private double quantity;
    private String color;
    private String size;
    private String imageUrl;

    public DtoOrderItemParent (OrderProduct entity){
        var inventory = entity.getInventory();
        var product = inventory.getProduct();
        this.id = entity.getId();
        this.productId = product.getId();
        this.name = product.getName();
        this.quantity = entity.getQuantity();
        this.size = inventory.getSize();
        this.color = inventory.getColor();
    }
}
