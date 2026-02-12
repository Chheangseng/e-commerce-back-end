package com.tcs.e_commerce_back_end.model.dto.userAccount;

import com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent.DtoUserEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserForgotPassword extends DtoUserEmail {
    @NotNull(message = "callBackUrl is required")
    @NotBlank(message = "callBackUrl is required")
    private String callBackUrl;
}
