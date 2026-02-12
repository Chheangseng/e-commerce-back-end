package com.tcs.e_commerce_back_end.model.entity.user;

import com.tcs.e_commerce_back_end.emuns.AddressType;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import jakarta.persistence.*;
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
public class UserAddress extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fullName;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "user_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "user_id"))
  private UserAccount userAccount;

  private String phoneNumber;
  private String email;
  private String address;
  private String addressDetail;
  private String city;
  private String state;
  private String zipcode;
  private String country;
  private boolean isDefault;

  @Enumerated(EnumType.STRING)
  private AddressType type;
}
