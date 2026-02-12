package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.product.PromotionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionProduct,Long> {
}
