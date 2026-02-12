package com.tcs.e_commerce_back_end.model.entity;

import com.tcs.e_commerce_back_end.model.entity.product.Product;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  private String logoId;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private Set<Product> products = new HashSet<>();

  public void removeAllProduct() {
    if (Objects.isNull(this.getProducts()) || this.getProducts().isEmpty()) {
      return;
    }
    for (Product product : this.getProducts()) {
      product.setCategory(null);
    }
    this.getProducts().clear();
  }
}
