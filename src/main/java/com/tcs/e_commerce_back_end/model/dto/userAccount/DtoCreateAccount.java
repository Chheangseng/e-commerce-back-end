package com.tcs.e_commerce_back_end.model.dto.userAccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoCreateAccount extends DtoUserLogin{
    private String phoneNumber;
    private String role;
    private String firstname;
    private String lastName;
}
