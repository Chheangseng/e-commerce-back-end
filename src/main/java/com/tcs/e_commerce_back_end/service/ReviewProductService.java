package com.tcs.e_commerce_back_end.service;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.review.DtoReviewList;
import com.tcs.e_commerce_back_end.model.dto.review.DtoReviewProduct;
import com.tcs.e_commerce_back_end.model.entity.product.ReviewProduct;
import com.tcs.e_commerce_back_end.repository.ReviewRepository;
import com.tcs.e_commerce_back_end.repository.product.ProductRepository;
import com.tcs.e_commerce_back_end.repository.user.UserRepository;
import com.tcs.e_commerce_back_end.service.product.ProductService;
import com.tcs.e_commerce_back_end.service.user.UserAuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewProductService {
  private final ReviewRepository repository;
  private final UserRepository userRepository;
  private final ProductService productService;

  private final ProductRepository productRepository;
  private final UserAuthService authService;

  public void assignRateToProduct(long productId, DtoReviewProduct dto) {
    var rate =
        repository.findById(ObjectUtils.defaultIfNull(dto.getId(), 0L)).orElse(new ReviewProduct());
    var findProduct = productService.findProductById(productId);
    var user = authService.securityContextUser();
    var userId = ObjectUtils.defaultIfNull(user.getId(), 0L);
    var havePurchased = repository.hasUserPurchasedProduct(userId, productId);
    if (Objects.isNull(dto.getId())) {
      findProduct.setReviewAmount(findProduct.getReviewAmount() + 1);
    }
    rate.setProduct(findProduct);
    rate.setComment(dto.getComment());
    rate.setUserAccount(user);
    rate.setRate(dto.getRate());
    rate.setHavePurchased(havePurchased);
    repository.save(rate);
    findProduct.setAverageRate(repository.averageRate(findProduct.getId()));
    productRepository.save(findProduct);
  }

  public List<DtoReviewList> getReviewByProduct(Long id) {
    List<DtoReviewList> reviewLists = new ArrayList<>();
    var list = repository.findByProductId(id);
    list.forEach(entity -> reviewLists.add(mapEntityToDTo(entity)));
    return reviewLists;
  }

  public List<DtoReviewList> getReviews() {
    List<DtoReviewList> reviewLists = new ArrayList<>();
    var list = repository.findAll();
    list.forEach(entity -> reviewLists.add(mapEntityToDTo(entity)));
    return reviewLists;
  }

  public DtoReviewList getReviewsById(long id) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new ApiExceptionStatusException("unable to find this review id: " + id, 400));
    return mapEntityToDTo(entity);
  }

  private DtoReviewList mapEntityToDTo(ReviewProduct entity) {
    var review = new DtoReviewList();
    var entityUser = userRepository.findById(entity.getUserAccount().getId()).orElse(null);
    review.setId(entity.getId());
    review.setCreateDate(entity.getCreatedAt());
    review.setRate(entity.getRate());
    review.setComment(entity.getComment());
    if (Objects.nonNull(entityUser)) {
      review.setFirstName(entityUser.getFirstName());
      review.setLastName(entityUser.getLastName());
    }
    return review;
  }
}
