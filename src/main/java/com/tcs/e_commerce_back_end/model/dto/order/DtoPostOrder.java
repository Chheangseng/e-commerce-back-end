package com.tcs.e_commerce_back_end.model.dto.order;


import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderItemParent;
import com.tcs.e_commerce_back_end.model.dto.order.parent.DtoOrderParent;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoPostOrder extends DtoOrderParent {
    @URL(message = "input valid url")
    private String successUrl;
    @URL(message = "input valid url")
    private String cancelUrl;
    @NotBlank(message = "require at least one item")
    private List<DtoOrderItemParent> productOrders;
    public DtoPostOrder (Orders entity){
        super(entity);
        this.productOrders = new ArrayList<>();
    }
}
