package com.tcs.e_commerce_back_end.model.mapper;

import com.tcs.e_commerce_back_end.model.dto.order.DtoItemOrderList;
import com.tcs.e_commerce_back_end.model.dto.order.DtoOrdersList;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;

public class OrderListMapper {
  public static DtoOrdersList mapItem(Orders entity) {
    var dto = new DtoOrdersList(entity);
    for (var entityItem : entity.getOrderProducts()) {
      var itemDto = new DtoItemOrderList(entityItem);
      itemDto.setPrice(entityItem.getUnitPrice());
      dto.getProductOrders().add(itemDto);
      dto.setProfit(
          dto.getProfit()
              .add(entityItem.getUnitPrice().subtract(entityItem.getInventory().getPrice())));
    }
    return dto;
  }
}
