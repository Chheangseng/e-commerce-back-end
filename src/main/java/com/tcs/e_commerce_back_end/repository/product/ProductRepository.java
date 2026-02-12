package com.tcs.e_commerce_back_end.repository.product;

import com.tcs.e_commerce_back_end.model.entity.product.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository
    extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
  Integer countAllByCategoryId(Long categoryId);

  @Query("select max(p.price) from Product p")
  Float findHighestPrice();

  @Query(
      "SELECT distinct p FROM Product p join ProductInventory pi on pi.product.id = p.id WHERE (:#{#categoryId.size()}=0 OR p.category.id IN (:categoryId)) AND "
          + "(LENGTH(TRIM(:search)) = 0 OR LOWER(TRIM(p.name)) LIKE LOWER(TRIM(CONCAT('%',:search,'%') ) )) AND "
          + "(:#{#color.size()}=0 OR pi.color IN (:color)) AND"
          + "((:priceMax <= 0) OR p.price BETWEEN :priceMin AND :priceMax) AND "
          + "((:rateMax <= 0) OR p.averageRate BETWEEN :rateMin AND :rateMax)")
  Page<Product> filterProduct(
      @Param("search") String search,
      @Param("categoryId") List<Long> categoryId,
      @Param("color") List<String> color,
      @Param("priceMin") int priceMin,
      @Param("priceMax") int priceMax,
      @Param("rateMin") int rateMin,
      @Param("rateMax") int rateMax,
      Pageable pageable);

  //  @Query(
  //          "SELECT distinct p FROM Product p left join ProductInventory pi on pi.product.id =
  // p.id WHERE"
  //                  + "(length(trim(:search)) = 0 OR lower(trim(p.name)) like
  // lower(trim(concat('%',:search,'%')))) AND" +
  //                  "(:#{#categoryId.size()}=0 OR p.category.id IN (:categoryId))")
  //  Page<Product> filterProductPage(Pageable pageable,
  //                                  @Param("search") String search,
  //                                  @Param("categoryId") List<Long> categoryId);

  List<Product> findAllByIdIn(@Param("ids") List<Long> ids);
}
