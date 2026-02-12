package com.tcs.e_commerce_back_end.model.entity.product;

import com.tcs.e_commerce_back_end.model.entity.Category;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
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
public class Product extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String imageId;
  private String description;
  private BigDecimal price;
  private BigDecimal sellPrice;

  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      mappedBy = "product",
      orphanRemoval = true)
  private Set<ProductInventory> productInventories = new HashSet<>();

  private double averageRate;
  private int totalSold;
  private int reviewAmount;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(
      name = "category_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_category"))
  private Category category;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "product_promotion",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "promotion_id"))
  private Set<PromotionProduct> promotions = new HashSet<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
  private Set<ReviewProduct> reviewProducts = new HashSet<>();

  public void addPromotion(PromotionProduct promotionProduct) {
    this.promotions.add(promotionProduct);
  }
}
