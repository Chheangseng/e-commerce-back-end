package com.tcs.e_commerce_back_end.model.dto.userAddress;

import com.tcs.e_commerce_back_end.emuns.AddressType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoUserAddressParent {
    private Long id;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String city;
    private String state;
    private String zipcode;
    private String country;
    private Boolean isDefault = false;

    private AddressType type;

}
