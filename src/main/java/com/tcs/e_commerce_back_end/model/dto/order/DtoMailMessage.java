package com.tcs.e_commerce_back_end.model.dto.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoMailMessage {
    @NotNull(message = "email is required")
    @NotBlank(message = "email is required")
    @Email(message = "email invalid")
    private String email;
    private String subject;
    @NotNull(message = "message is required")
    @NotBlank(message = "message is required")
    private String message;
}
