package com.tcs.e_commerce_back_end.service.authcode;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.oneTimeCode.DtoOTC;
import com.tcs.e_commerce_back_end.model.entity.OneTimeCodeModel;
import com.tcs.e_commerce_back_end.repository.OneTimeCodeRepository;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OneTimeCodeService {
  private final OneTimeCodeRepository repository;

  public String generateCode(String value) {
    var response = repository.save(new OneTimeCodeModel(UUID.randomUUID().toString(), value));
    return response.getId();
  }

  public String generateCode() {
    var response = repository.save(new OneTimeCodeModel(UUID.randomUUID().toString()));
    return response.getId();
  }

  public DtoOTC useCode(String code) {
    var response =
        repository
            .findById(code)
            .orElseThrow(() -> new ApiExceptionStatusException("invalid code exception", 400));
    repository.deleteById(code);
    return new DtoOTC(response);
  }
}
