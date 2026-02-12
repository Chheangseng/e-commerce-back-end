package com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent;

import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DtoUserEmail {
    @NotNull(message = "email is required")
    @NotBlank
    @Email(message = "email invalid")
    private String email;

    public DtoUserEmail (UserAccount entity){
        this.email = entity.getEmail();
    }
}
