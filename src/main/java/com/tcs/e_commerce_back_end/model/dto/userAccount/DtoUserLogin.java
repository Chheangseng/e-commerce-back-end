package com.tcs.e_commerce_back_end.model.dto.userAccount;

import com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent.DtoUserEmail;
import jakarta.validation.constraints.Email;
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
public class DtoUserLogin extends DtoUserEmail {

    @NotNull(message = "password is required")
    @NotBlank(message = "password is required")
    private String password;

    public DtoUserLogin(@NotNull(message = "email is required") @NotBlank @Email(message = "email invalid") String email, String password) {
        super(email);
        this.password = password;
    }
}
