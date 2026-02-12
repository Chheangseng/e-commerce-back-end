package com.tcs.e_commerce_back_end.model.dto.review;

import com.tcs.e_commerce_back_end.model.entity.product.ReviewProduct;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoReviewList {
  private Long id;
  private String firstName;
  private String lastName;
  private boolean havePurchased;
  private Double rate;
  private String comment;
  private Date createDate;

  public DtoReviewList(ReviewProduct entity, UserAccount userAccount) {
    this.id = entity.getId();
    this.firstName = userAccount.getFirstName();
    this.lastName = userAccount.getLastName();
    this.rate = entity.getRate();
    this.comment = entity.getComment();
    this.createDate = entity.getCreatedAt();
    this.havePurchased = entity.getHavePurchased();
  }
}
