package com.tcs.e_commerce_back_end.model.dto.order;

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
public class DtoOrderDetail extends DtoOrderParent {
    private List<DtoOrderItem> productOrders;
    public DtoOrderDetail(Orders entity){
        super(entity);
        this.productOrders = new ArrayList<>();
    }
}

