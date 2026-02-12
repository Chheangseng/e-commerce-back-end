package com.tcs.e_commerce_back_end.model.modelAttribute;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ModelPagination {
  private int page = 1;
  private int size = 10;
  private String field;
  private String direction;

  public void setPage(int page) {
    this.page = Math.max(1, page); // Ensure page is non-negative
  }

  public void setSize(int size) {
    this.size =
        Math.max(1, Math.min(size, 500)); // Ensure page is non-negative and max page size is 500
  }

  @JsonIgnore
  public Pageable toPageable() {
    if (Objects.isNull(this.field) || Objects.isNull(this.direction)) {
      return PageRequest.of(this.page - 1, this.size);
    }
    return PageRequest.of(
        this.page - 1, this.size, Sort.by(Sort.Direction.fromString(this.direction), this.field));
  }
}
