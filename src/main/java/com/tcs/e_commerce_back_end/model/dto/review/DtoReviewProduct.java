package com.tcs.e_commerce_back_end.model.dto.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoReviewProduct {
    private Long id;
    private Double rate;
    private String comment;
}
