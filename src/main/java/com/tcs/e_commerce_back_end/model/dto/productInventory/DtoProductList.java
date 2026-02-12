package com.tcs.e_commerce_back_end.model.dto.productInventory;

import com.tcs.e_commerce_back_end.model.dto.category.CategoryNameDto;
import com.tcs.e_commerce_back_end.model.dto.productInventory.DtoProductParent.DtoProductParent;
import com.tcs.e_commerce_back_end.model.dto.promotion.DtoPromotionList;
import com.tcs.e_commerce_back_end.model.entity.product.Product;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoProductList extends DtoProductParent {
    private List<String> imageIds;
    private double discountPrice;
    private double rating;
    private CategoryNameDto category;
    private int soldAmount;
    private boolean isFavorite;
    private DtoPromotionList promotion;
    private long createDate;
    private List<DtoProductInventory> productInventories;
    public DtoProductList(Product entity) {
        super(entity);
        if (Objects.nonNull(entity.getImageId())) {
            this.imageIds = Arrays.asList(entity.getImageId().split(", "));
        }
        this.rating = entity.getAverageRate();
        this.soldAmount = ObjectUtils.defaultIfNull(entity.getTotalSold(), 0);
        this.isFavorite = false;
        if (Objects.nonNull(entity.getPromotions()) && !entity.getPromotions().isEmpty()) {
            var promotionProduct = entity.getPromotions().stream().toList().get(0);
            if (promotionProduct.getEndDate().before(new Date())) {
                this.discountPrice = promotionProduct.getDiscountPrice();
                this.promotion = new DtoPromotionList(promotionProduct);
            }
        }
        if ( Objects.nonNull(entity.getProductInventories()) && !entity.getProductInventories().isEmpty()){
            List<DtoProductInventory> productStock = new ArrayList<>();
            entity.getProductInventories().forEach(productInventory -> productStock.add(new DtoProductInventory(productInventory)));
            this.productInventories = productStock;
        }
        this.createDate = entity.getCreatedAt().getTime();
        var category = entity.getCategory();
        if (Objects.nonNull(category)){
            this.category = new CategoryNameDto(category.getId(),category.getName());
        }
    }
}
