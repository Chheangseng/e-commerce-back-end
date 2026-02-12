package com.tcs.e_commerce_back_end.model.dto.order.parent;

import com.tcs.e_commerce_back_end.emuns.DeliveryType;
import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.PaymentMethod;
import com.tcs.e_commerce_back_end.emuns.RegistrationStatus;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrderParent {
  private long id;
  private Long userId;
  private String fullName;
  private String email;
  private String phoneNumber;
  private String address;
  private String addressDetail;
  private String city;
  private String country;
  private String postCode;
  private PaymentMethod paymentMethod;
  private DeliveryType deliveryType;
  private OrderStatus status;
  private long createdDate;
  private double total;
  private String receiptId;

  public DtoOrderParent(Orders entity) {
    if (entity.getUserAccount() != null) {
      this.userId = entity.getUserAccount().getId();
    }
    this.id = entity.getId();
    this.fullName = entity.getFullName();
    this.email = entity.getEmail();
    this.phoneNumber = entity.getPhoneNumber();
    this.address = entity.getAddress();
    this.addressDetail = entity.getAddressDetail();
    this.city = entity.getCity();
    this.country = entity.getCountry();
    this.paymentMethod = entity.getPaymentMethod();
    this.status = entity.getStatus();
    this.postCode = entity.getPostCode();
    this.deliveryType = entity.getDeliveryType();
    if (Objects.nonNull(entity.getTotalAmount())) {
      this.total = entity.getTotalAmount().doubleValue();
    }
    if (Objects.nonNull(entity.getCreatedAt())) {
      this.createdDate = entity.getCreatedAt().getTime();
    }
    this.receiptId = entity.getReceiptId();
  }

  public Orders convertToOrdersEntity() {
    var entity = new Orders();
    return entityMapper(entity);
  }

  public void convertToOrdersEntity(Orders entity) {
    entityMapper(entity);
  }

  private Double priceFormat(double value) {
    return Math.round(value * 100.0) / 100.0;
  }

  private Orders entityMapper(Orders entity) {
    if (Objects.nonNull(entity.getId())) {
      entity.setId(this.id);
    }
    entity.setDeliveryType(this.deliveryType);
    entity.setEmail(this.email);
    entity.setPhoneNumber(this.phoneNumber);
    entity.setFullName(this.fullName);
    entity.setOrderDate(new Date());
    entity.setAddressDetail(this.addressDetail);
    entity.setAddress(this.address);
    entity.setCity(this.city);
    entity.setCountry(this.country);
    entity.setPaymentMethod(this.paymentMethod);
    entity.setPostCode(this.postCode);
    entity.setTotalAmount(BigDecimal.valueOf(this.total));
    if (Objects.isNull(this.userId)) {
      entity.setRegistrationStatus(RegistrationStatus.UNREGISTER);
    } else {
      entity.setRegistrationStatus(RegistrationStatus.REGISTER);
    }
    if (Objects.isNull(this.status)) {
      entity.setStatus(OrderStatus.WAITING_FOR_SHIP);
    } else {
      entity.setStatus(this.status);
    }
    entity.setReceiptId(
        ObjectUtils.defaultIfNull(entity.getReceiptId(), UUID.randomUUID().toString()));
    return entity;
  }
}
