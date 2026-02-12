package com.tcs.e_commerce_back_end.model.entity.order;

import com.tcs.e_commerce_back_end.emuns.DeliveryType;
import com.tcs.e_commerce_back_end.emuns.OrderStatus;
import com.tcs.e_commerce_back_end.emuns.PaymentMethod;
import com.tcs.e_commerce_back_end.emuns.RegistrationStatus;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_userId"))
  private UserAccount userAccount;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "orders")
  private Set<OrderProduct> orderProducts = new HashSet<>();

  private String email;
  private String phoneNumber;
  private String fullName;
  private Date orderDate;
  private String address;
  private String addressDetail;
  private String country;
  private String city;
  private String postCode;
  private String receiptId;

  @Enumerated(EnumType.STRING)
  private RegistrationStatus registrationStatus;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @Enumerated(EnumType.STRING)
  private DeliveryType deliveryType;

  private BigDecimal totalAmount;
  private BigDecimal profit;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private boolean havePay;
}
