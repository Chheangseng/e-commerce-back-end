package com.tcs.e_commerce_back_end.model.dto.oneTimeCode;

import com.tcs.e_commerce_back_end.model.entity.OneTimeCodeModel;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoOTC {
  private String code;
  private String value;

  public DtoOTC(OneTimeCodeModel model) {
    this.code = model.getId();
    this.value = model.getValue();
  }

  public Optional<String> getValue() {
    if (Objects.isNull(this.value)) {
      return Optional.empty();
    }
    return Optional.of(this.value);
  }
}
