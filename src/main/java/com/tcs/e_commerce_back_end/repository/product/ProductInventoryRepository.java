package com.tcs.e_commerce_back_end.repository.product;

import com.tcs.e_commerce_back_end.model.entity.product.ProductInventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory,Long> {
    @Query("SELECT v.color FROM ProductInventory v")
    List<String> getAvailableColor ();

    List<ProductInventory> findAllByIdIn(List<Long> productId);
    @Query("SELECT v FROM ProductInventory v WHERE v.product.id = :productId AND v.color = :color AND v.size = :size")
    Optional<ProductInventory> findByProductIdColorSize(@Param("productId") Long productId,
                                                           @Param("color") String color,
                                                           @Param("size") String size);
}
