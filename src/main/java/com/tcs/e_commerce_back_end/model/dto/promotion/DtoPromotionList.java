package com.tcs.e_commerce_back_end.model.dto.promotion;
import com.tcs.e_commerce_back_end.model.entity.product.PromotionProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoPromotionList {
    private Long id;
    private String title;
    private String description;
    private Long startDate;
    private Long endDate;
    private Double discountPercentage;
    private Double discountPrice;

    public DtoPromotionList(PromotionProduct promotion) {
        this.id = promotion.getId();
        this.description = promotion.getDescription();
        this.title = promotion.getTitle();
        this.startDate = promotion.getStartDate().getTime();
        this.endDate = promotion.getEndDate().getTime();
        this.discountPercentage = promotion.getDiscountPercentage();
        this.discountPrice = promotion.getDiscountPrice();
    }
}
