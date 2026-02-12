package com.tcs.e_commerce_back_end.model.dto.order;

import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderItemParent;
import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrderItem extends DtoOrderItemParent {
    private String name ;
    private Double price;
    private List<String> idImages;

    public DtoOrderItem(OrderProduct entity) {
        super(entity);
        var product = entity.getInventory().getProduct();
        this.name = product.getName();
        this.price = entity.getUnitPrice().doubleValue();
        if (Objects.nonNull(product.getImageId())){
            this.idImages = Arrays.stream(product.getImageId().split(", ")).toList();
        }
    }
}
