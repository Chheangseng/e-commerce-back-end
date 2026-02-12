package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.model.dto.review.DtoReviewList;
import com.tcs.e_commerce_back_end.model.dto.review.DtoReviewProduct;
import com.tcs.e_commerce_back_end.service.ReviewProductService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {
  private final ReviewProductService service;

  public ReviewController(ReviewProductService service) {
    this.service = service;
  }

  @PostMapping("/v1/review/{productId}")
  public ResponseEntity<Void> assignRate(
      @PathVariable Long productId, @RequestBody DtoReviewProduct dtoRatePost) {
    service.assignRateToProduct(productId, dtoRatePost);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/review")
  public ResponseEntity<List<DtoReviewList>> getList() {
    return ResponseEntity.ok(service.getReviews());
  }

  @GetMapping("/review/{id}")
  public ResponseEntity<DtoReviewList> getReviewId(@PathVariable Long id) {
    return ResponseEntity.ok(service.getReviewsById(id));
  }

  @GetMapping("/review/product/{id}")
  public ResponseEntity<List<DtoReviewList>> getReviewByProduct(@PathVariable long id) {
    return ResponseEntity.ok(service.getReviewByProduct(id));
  }
}
