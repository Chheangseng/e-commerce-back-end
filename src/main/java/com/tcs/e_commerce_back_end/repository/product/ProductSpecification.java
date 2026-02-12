package com.tcs.e_commerce_back_end.repository.product;

import com.tcs.e_commerce_back_end.model.entity.product.Product;
import com.tcs.e_commerce_back_end.model.modelAttribute.ModelProductParam;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProductSpecification {
  public static Specification<Product> advanceProductFilter(ModelProductParam param){
    Specification<Product> spec = Specification.anyOf();
    if (StringUtils.hasLength(param.getSearch())){
      spec = spec.and(ProductSpecification.searchProduct(param.getSearch()));
    }
    if (Objects.nonNull(param.getListCategoriesIds()) && !param.getListCategoriesIds().isEmpty()){
      spec = spec.and(ProductSpecification.filterCategory(param.getListCategoriesIds()));
    }
    if (Objects.nonNull(param.getListColors()) && !param.getListColors().isEmpty()){
      spec = spec.and(ProductSpecification.filterColor(param.getListColors()));
    }
    if (param.getPriceMin() > 0 || param.getPriceMax() > 0){
      spec = spec.and(ProductSpecification.filterSellPrice(param.getPriceMin(),param.getPriceMax()));
    }
    if (param.getRateMin() > 0 || param.getRateMax() > 0) {
      spec = spec.and(ProductSpecification.filterRating(param.getRateMin(),param.getRateMax()));
    }
    return spec;
  }
  public static Specification<Product> searchProduct(String search) {
    return (root, query, criteriaBuilder) -> {
      String likePattern = "%" + search.toLowerCase() + "%";
      return criteriaBuilder.or(
          criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likePattern),
          criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern));
    };
  }

  public static Specification<Product> filterCategory(List<Long> categories) {
    return (root, query, criteriaBuilder) -> {
      query.distinct(true);
      Join<Object, Object> categoryJoin = root.join("category", JoinType.INNER);
      CriteriaBuilder.In<Long> inClause = criteriaBuilder.in(categoryJoin.get("id"));
      for (Long category : categories) {
        inClause.value(category);
      }
      return inClause;
    };
  }

  public static Specification<Product> filterColor(List<String> colors) {
    return (root, query, criteriaBuilder) -> {
      query.distinct(true);
      Join<Object, Object> inventoryJoin = root.join("productInventories", JoinType.INNER);
      CriteriaBuilder.In<String> inClause = criteriaBuilder.in(inventoryJoin.get("color"));
      for (String category : colors) {
        inClause.value(category);
      }
      return inClause;
    };
  }

  public static Specification<Product> filterRating(int min, int max) {
    return (root, query, criteriaBuilder) -> {
      query.distinct(true);
      Join<Object, Object> review = root.join("reviewProducts", JoinType.INNER);
      if (min > 0 && max > 0 && min <= max) {
        return criteriaBuilder.between(review.get("rate"), min, max);
      } else if (min > 0) {
        return criteriaBuilder.greaterThanOrEqualTo(review.get("rate"), min);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(review.get("rate"), max);
      }
    };
  }
  public static Specification<Product> filterPrice(int min, int max) {
    return (root, query, criteriaBuilder) -> {
      if (min > 0 && max > 0 && min <= max) {
        return criteriaBuilder.between(root.get("price"), min, max);
      } else if (min > 0) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
      }
    };
  }
  public static Specification<Product> filterSellPrice(int min, int max) {
    return (root, query, criteriaBuilder) -> {
      if (min > 0 && max > 0 && min <= max) {
        return criteriaBuilder.between(root.get("sellPrice"), min, max);
      } else if (min > 0) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("sellPrice"), min);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get("sellPrice"), max);
      }
    };
  }
  public static Specification<Product> getHeightestPrice(int min, int max) {
    return (root, query, criteriaBuilder) -> {
      if (min > 0 && max > 0 && min <= max) {
        return criteriaBuilder.between(root.get("sellPrice"), min, max);
      } else if (min > 0) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("sellPrice"), min);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get("sellPrice"), max);
      }
    };
  }
}
