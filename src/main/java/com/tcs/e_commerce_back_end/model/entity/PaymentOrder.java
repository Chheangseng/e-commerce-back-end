package com.tcs.e_commerce_back_end.model.entity;


import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import jakarta.persistence.*;
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
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_order"))
    private Orders order;
    private boolean isComplete;
}
