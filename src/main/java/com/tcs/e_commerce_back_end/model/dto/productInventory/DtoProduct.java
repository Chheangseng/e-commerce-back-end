package com.tcs.e_commerce_back_end.model.dto.productInventory;

import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductParent.DtoProductParent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DtoProduct extends DtoProductParent {

    private Long categoryId;

    private List<DtoProductInventory> productInventories;

    public List<DtoProductInventory> getProductInventories() {
        if (Objects.isNull(this.productInventories)){
            this.productInventories = new ArrayList<>();
        }
        return this.productInventories;
    }
}
