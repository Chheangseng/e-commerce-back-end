package com.tcs.e_commerce_back_end.service.stripService.dto;

import com.tcs.e_commerce_back_end.service.stripService.enums.SupportCurrency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoItem {
    @NotBlank(message = "name is require")
    private String name;
    private String description;
    private String urlImage;
    @Schema(type = "long", example = "1")
    private Long unitPrice;
    @Schema(description = "Payment currency", example = "USD")
    private SupportCurrency currency;
    @Schema(type = "long", example = "1")
    private Long quantity;
}
