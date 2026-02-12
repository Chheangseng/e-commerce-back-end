package com.tcs.e_commerce_back_end.model.dto.order;

import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderItemParent;
import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderParent;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoUpdateOrder extends DtoOrderParent {
    private List<DtoOrderItemParent> productOrders;

    public DtoUpdateOrder(Orders entity) {
        super(entity);
        this.productOrders = new ArrayList<>();
    }
}
