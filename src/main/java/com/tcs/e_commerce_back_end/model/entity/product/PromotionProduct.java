package com.tcs.e_commerce_back_end.model.entity.product;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
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
public class PromotionProduct extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private Date startDate;
  private Date endDate;
  private double discountPercentage;
  private double discountPrice;
  @ManyToMany(
      mappedBy = "promotions",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE})
  private Set<Product> products = new HashSet<>();
}
