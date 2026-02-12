package com.tcs.e_commerce_back_end.model.dto.userAccount;

import com.tcs.e_commerce_back_end.emuns.AddressType;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import com.tcs.e_commerce_back_end.model.entity.user.UserAddress;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoUserAddress {
  private Long id;
  private Long userId;
  private String fullName;
  private String phoneNumber;
  private String email;
  private String address;
  private String city;
  private String state;
  private String zipcode;
  private String country;
  private boolean isDefault;
  private AddressType type;
  @JsonIgnore
  public DtoUserAddress (UserAddress userAddress){
    this.id = userAddress.getId();
    this.userId = userAddress.getUserAccount().getId();
    this.phoneNumber = userAddress.getPhoneNumber();
    this.address = userAddress.getAddress();
    this.city = userAddress.getCity();
    this.state = userAddress.getState();
    this.zipcode = userAddress.getZipcode();
    this.isDefault = ObjectUtils.getIfNull(userAddress.isDefault(),false);
    this.type = userAddress.getType();
    this.fullName = userAddress.getFullName();
    this.email = userAddress.getEmail();
  }
  public UserAddress toUserAddress(UserAccount userAccount) {
    UserAddress userAddress = new UserAddress();
    return assignUserAddress(userAddress,userAccount);
  }
  public UserAddress toUserAddress(UserAddress userAddress, UserAccount userAccount){
    return assignUserAddress(userAddress,userAccount);
  }
  private UserAddress assignUserAddress(UserAddress userAddress, UserAccount userAccount){
    userAddress.setFullName(ObjectUtils.getIfNull(this.fullName,userAccount.getFirstName() + " " + userAccount.getLastName()));
    userAddress.setEmail(ObjectUtils.getIfNull(this.email,userAccount.getEmail()));
    userAddress.setId(this.id);
    userAddress.setUserAccount(userAccount); // Assuming you pass a UserAccount object
    userAddress.setPhoneNumber(this.phoneNumber);
    userAddress.setAddress(this.address);
    userAddress.setCity(this.city);
    userAddress.setState(this.state);
    userAddress.setZipcode(this.zipcode);
    userAddress.setDefault(this.isDefault);
    userAddress.setType(this.type);
    return userAddress;
  }
}
