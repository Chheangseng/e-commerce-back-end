package com.tcs.e_commerce_back_end.model.entity.product;

import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import com.tcs.e_commerce_back_end.model.entity.order.OrderProduct;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInventory extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "product_invetory_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_product_inventory"))
  private Product product;

  private BigDecimal price;
  private BigDecimal sellPrice;
  private String color;
  private String size;
  private double stock;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(columnDefinition = "jsonb")
  private Map<String, Object> attributes = new HashMap<>();

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      mappedBy = "inventory")
  private Set<OrderProduct> orderProducts = new HashSet<>();

  public void addProduct(Product parent) {
    this.product = parent;
    this.product.getProductInventories().add(this);
  }
}
