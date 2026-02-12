package com.tcs.e_commerce_back_end.model.dto.userAccount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoResetPassword {
    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    String password;

    String resetToken;
}
