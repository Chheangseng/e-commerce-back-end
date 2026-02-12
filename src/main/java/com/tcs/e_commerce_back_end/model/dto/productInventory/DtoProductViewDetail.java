package com.tcs.e_commerce_back_end.model.dto.productInventory;


import com.tcs.e_commerce_back_end.model.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductViewDetail extends DtoProductList{
    private boolean allowToReview = false;
    private int reviewsAmount;

    public DtoProductViewDetail(Product entity) {
        super(entity);
        this.reviewsAmount = entity.getReviewAmount();
    }
}
