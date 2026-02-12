package com.tcs.e_commerce_back_end.model.dto.order;

import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderParent;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import java.math.BigDecimal;
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
public class DtoOrdersList extends DtoOrderParent {
    private List<DtoItemOrderList> productOrders;
    private BigDecimal profit = BigDecimal.ZERO;

    public DtoOrdersList(Orders entity) {
        super(entity);
        this.productOrders = new ArrayList<>();
    }
}
