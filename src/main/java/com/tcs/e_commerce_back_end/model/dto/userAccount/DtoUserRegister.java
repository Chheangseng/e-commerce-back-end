package com.tcs.e_commerce_back_end.model.dto.userAccount;

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
public class DtoUserRegister extends DtoUserLogin{
    @NotNull(message = "firstName is required")
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotNull(message = "lastName is required")
    @NotBlank(message = "lastName is required")
    private String lastName;
}
