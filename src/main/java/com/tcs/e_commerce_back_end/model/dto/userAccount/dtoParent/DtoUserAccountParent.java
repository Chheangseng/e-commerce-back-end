package com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent;

import com.tcs.e_commerce_back_end.emuns.Role;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DtoUserAccountParent{
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    @JsonIgnore
    public DtoUserAccountParent (UserAccount entity){
        this.email = entity.getEmail();
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.phoneNumber = entity.getPhoneNumber();
    }
    @JsonIgnore
    public void toUserAccount(UserAccount userAccount) {
        setUserAccount(userAccount);
    }
    @JsonIgnore
    public void setUserAccount (UserAccount userAccount){
        if (Objects.nonNull(this.id)){
            userAccount.setId(this.id);
        }
        userAccount.setEmail(this.getEmail());
        userAccount.setFirstName(this.firstName);
        userAccount.setLastName(this.lastName);
        userAccount.setPhoneNumber(this.phoneNumber);
        if (Objects.isNull(userAccount.getStatus())){
            userAccount.setStatus(Role.USER);
        }
    }
}
