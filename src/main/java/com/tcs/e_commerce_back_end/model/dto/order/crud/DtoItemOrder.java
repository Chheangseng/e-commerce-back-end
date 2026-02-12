package com.tcs.e_commerce_back_end.model.dto.order.crud;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoItemOrder {
    private Long inventoryId;
    private Double quantity;
}
