package com.tcs.e_commerce_back_end.model.entity.order;

import com.tcs.e_commerce_back_end.model.entity.product.ProductInventory;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "order_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_orderId"))
    private Orders orders;
    private double quantity;
    private BigDecimal unitPrice;
    private String color;
    private String size;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "product_inventory_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_product_inventory_Id"))
    private ProductInventory inventory;
}
