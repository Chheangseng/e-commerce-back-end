package com.tcs.e_commerce_back_end.model.mapper;

import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.RegistrationStatus;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoCreateOrder;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoItemOrder;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoOrderUserInfo;
import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import com.tcs.e_commerce_back_end.model.entity.product.ProductInventory;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class OrderMapper {

  public static OrderProduct mapItem(Orders orders, DtoItemOrder dto, ProductInventory inventory) {
    var prev = new OrderProduct();
    prev.setInventory(inventory);
    prev.setQuantity(dto.getQuantity());
    prev.setUnitPrice(inventory.getSellPrice());
    prev.setColor(inventory.getColor());
    prev.setSize(inventory.getSize());
    prev.setOrders(orders);
    return prev;
  }

  public static Orders mapToEntity(DtoCreateOrder dto) {
    var entity = new Orders();
    if (Objects.nonNull(dto.getUserInfo())) {
      mapAddress(dto.getUserInfo(), entity);
    }
    entity.setOrderDate(Date.from(Instant.now()));
    entity.setDeliveryType(dto.getDeliveryType());
    entity.setPaymentMethod(dto.getPaymentMethod());
    entity.setReceiptId(UUID.randomUUID().toString());
    entity.setRegistrationStatus(RegistrationStatus.REGISTER);
    entity.setStatus(OrderStatus.WAITING_FOR_SHIP);
    return entity;
  }

  public static void mapTotalAndProfile(Orders entities) {
    var profitTrack = BigDecimal.ZERO;
    var total = BigDecimal.ZERO;
    for (var item : entities.getOrderProducts()) {
      total = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
      var itemProfit =
          item.getUnitPrice()
              .subtract(item.getInventory().getPrice())
              .multiply(BigDecimal.valueOf(item.getQuantity()));
      profitTrack = profitTrack.add(itemProfit);
    }
    entities.setProfit(profitTrack);
    entities.setTotalAmount(total);
  }

  private static void mapAddress(DtoOrderUserInfo dto, Orders entity) {
    entity.setEmail(dto.getEmail());
    entity.setFullName(dto.getFullName());
    entity.setPhoneNumber(dto.getPhoneNumber());
    entity.setAddress(dto.getAddress());
    entity.setAddressDetail(dto.getAddressDetail());
    entity.setCountry(dto.getCountry());
    entity.setCity(dto.getCity());
    entity.setPostCode(dto.getPostCode());
  }
}
