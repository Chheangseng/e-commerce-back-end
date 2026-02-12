package com.tcs.e_commerce_back_end.model.dto.order.crud;

import com.tcs.e_commerce_back_end.emuns.DeliveryType;
import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoCreateOrder {
  @URL(message = "input valid url")
  private String successUrl;

  @URL(message = "input valid url")
  private String cancelUrl;

  @NotBlank(message = "require at least one item")
  private List<DtoItemOrder> productOrders;

  private DtoOrderUserInfo userInfo;
  private OrderStatus status;
  private PaymentMethod paymentMethod;
  private DeliveryType deliveryType;
}
