package com.tcs.e_commerce_back_end.model.dto.order;

import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderItemParent;
import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoItemOrderList extends DtoOrderItemParent {
  private BigDecimal price;

  public DtoItemOrderList(OrderProduct entity) {
    super(entity);
    this.price = BigDecimal.ZERO;
  }
}
