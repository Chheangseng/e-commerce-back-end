package com.tcs.e_commerce_back_end.service.stripService.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoCreateCheckout {
    @URL(message = "input valid url")
    private String successUrl;
    @URL(message = "input valid url")
    private String cancelUrl;
    private List<DtoItem> items;
}
