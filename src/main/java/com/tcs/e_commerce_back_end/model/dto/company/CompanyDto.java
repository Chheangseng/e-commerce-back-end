package com.tcs.e_commerce_back_end.model.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class CompanyDto {
    @NotNull(message = "name is required")
    @NotBlank(message = "name is required")
    private String name;
    private MultipartFile file;
}
