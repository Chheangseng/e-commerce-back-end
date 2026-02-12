package com.tcs.e_commerce_back_end.model.dto.order.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoOrderUserInfo {
    private String receiptId;
    private Long userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String city;
    private String country;
    private String postCode;
}
