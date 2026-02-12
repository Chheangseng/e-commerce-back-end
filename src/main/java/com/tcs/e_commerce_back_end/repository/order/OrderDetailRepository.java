package com.tcs.e_commerce_back_end.repository.order;

import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface OrderDetailRepository extends JpaRepository<OrderProduct,Long> {
    public List<OrderProduct> findAllByOrdersId(Long id);
}
