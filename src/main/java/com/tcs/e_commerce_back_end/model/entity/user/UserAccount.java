package com.tcs.e_commerce_back_end.model.entity.user;

import com.tcs.e_commerce_back_end.emuns.Gender;
import com.tcs.e_commerce_back_end.emuns.Role;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import com.tcs.e_commerce_back_end.model.entity.product.ReviewProduct;
import jakarta.persistence.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "user_account",
        indexes = {
                @Index(name = "idx_username", columnList = "username")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAccount extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private boolean isActivate;
  private String profileId;
  private String firstName;
  private String lastName;
  private String password;
  @Column(nullable = false, unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String phoneNumber;
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Role status;

  private String resetToken;

  @Column(name = "roles")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(
      name = "roles",
      joinColumns =
          @JoinColumn(
              name = "user_account_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = "fk_user_account")))
  private Set<String> roles = new HashSet<>();

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      mappedBy = "userAccount")
  private Set<Orders> orders = new HashSet<>();

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      mappedBy = "userAccount")
  private Set<ReviewProduct> reviewProducts = new HashSet<>();

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "userAccount",
      orphanRemoval = true)
  private Set<UserAddress> userAddresses = new HashSet<>();

  public void removeAddress(UserAddress address) {
    this.userAddresses.remove(address);
    address.setUserAccount(null);
  }

  public void addAddress(UserAddress address) {
    this.userAddresses.add(address);
    address.setUserAccount(this);
  }
}
