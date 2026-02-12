package com.tcs.e_commerce_back_end.model.mapper;

import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoCreateOrder;
import com.tcs.e_commerce_back_end.model.dto.order.crud.DtoItemOrder;
import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.service.stripService.dto.DtoCreateCheckout;
import com.tcs.e_commerce_back_end.service.stripService.dto.DtoItem;
import com.tcs.e_commerce_back_end.service.stripService.enums.SupportCurrency;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;

public class PaymentMapper {
  public static DtoCreateCheckout checkout(DtoCreateOrder dtoPostOrder) {
    return new DtoCreateCheckout(
        dtoPostOrder.getSuccessUrl(), dtoPostOrder.getCancelUrl(), new ArrayList<>());
  }

  public static DtoItem mapItem(DtoItemOrder item, Product product, HttpServletRequest request) {
    var payItem = new DtoItem();
    payItem.setUrlImage(mapUrlProductImage(product.getImageId(), request));
    payItem.setName(product.getName());
    payItem.setCurrency(SupportCurrency.USD);
    payItem.setQuantity(item.getQuantity().longValue());
    payItem.setUnitPrice(product.getSellPrice().longValue());
    payItem.setDescription(product.getDescription());
    return payItem;
  }

  public static String mapUrlProductImage(String id, HttpServletRequest request) {
    String scheme = request.getScheme(); // http or https
    String host = request.getServerName(); // localhost or domain
    int port = request.getServerPort();

    String baseUrl = scheme + "://" + host + (port != 80 && port != 443 ? ":" + port : "");
    return baseUrl + "/products/image/" + id;
  }
}
