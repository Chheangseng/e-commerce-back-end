package com.tcs.e_commerce_back_end.model.entity.product;

import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(name = "key_unique",columnNames = {
        "user_account_id",
        "product_id",
        "order_id"
}))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewProduct extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_account_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_user_account"))
    private UserAccount userAccount;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "product_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_product"))
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "order_id", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_order"))
    private Orders orders;
    private Double rate;
    private String comment;
    private Boolean havePurchased;
}
