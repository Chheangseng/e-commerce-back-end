package com.tcs.e_commerce_back_end.repository.order;

import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
  @Query(
      "SELECT o FROM  Orders o where (LENGTH(TRIM(:status)) = 0 OR TRIM(o.status) = :status) AND "
          + "(LENGTH(TRIM(:search)) = 0 OR  LOWER(TRIM(o.fullName)) LIKE LOWER(CONCAT('%',:search,'%') )) AND  "
          + "((:userId <=0) OR o.userAccount.id = :userId )")
  Page<Orders> filterOrders(
      @Param("status") String status,
      @Param("search") String search,
      @Param("userId") Long userId,
      Pageable pageable);

  List<Orders> findAllByUserAccountId(Long id);
}
