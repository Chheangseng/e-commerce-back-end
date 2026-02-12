package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.product.ReviewProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewProduct, Long> {
  @Query("SELECT AVG(r.rate)from ReviewProduct r where r.product.id = :productId")
  Double averageRate(@Param("productId") Long productId);

  List<ReviewProduct> findByProductId(Long productId);

  @Query(
      "SELECT CASE WHEN COUNT(od) > 0 THEN TRUE ELSE FALSE END "
          + "FROM Orders od "
          + "JOIN od.orderProducts o "
          + "WHERE od.userAccount.id = :userId "
          + "AND o.inventory.product.id = :productId")
  boolean hasUserPurchasedProduct(@Param("userId") Long userId, @Param("productId") Long productId);
}
