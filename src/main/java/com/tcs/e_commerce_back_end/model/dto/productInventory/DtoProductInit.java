package com.tcs.e_commerce_back_end.model.dto.productInventory;

import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductParent.DtoProductParent;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class DtoProductInit extends DtoProductParent {
    private String categoryName;

    private List<DtoProductInventory> productInventories = new ArrayList<>();
    
    public List<DtoProductInventory> removeInventoriesId () {
        return this.productInventories.stream().map(v-> {
            v.setId(null);
            return v;
        }).toList();
    }
}
