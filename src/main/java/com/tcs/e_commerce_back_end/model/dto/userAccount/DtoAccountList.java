package com.tcs.e_commerce_back_end.model.dto.userAccount;


import com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent.DtoUserAccountParent;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoAccountList extends DtoUserAccountParent {
    private int totalOrder;
    private boolean isActivate;
    public DtoAccountList(UserAccount account){
        super(account);
        this.isActivate = account.isActivate();
    }
}
