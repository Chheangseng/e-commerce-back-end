package com.tcs.e_commerce_back_end.service.user;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoUserAddress;
import com.tcs.e_commerce_back_end.repository.user.UserAddressRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAddressService {
  private final UserAddressRepository repository;
  private final UserAuthService authService;

  public List<DtoUserAddress> getAddressByUser() {
    var user = authService.securityContextUser();
    var entity = repository.findAllByUserAccountId(user.getId());
    List<DtoUserAddress> result = new ArrayList<>();
    entity.forEach(
        userAddress -> {
          result.add(new DtoUserAddress(userAddress));
        });
    return result;
  }

  public DtoUserAddress createUserAddress(DtoUserAddress dtoUserAddress) {
    var user = authService.securityContextUser();
    var entity = dtoUserAddress.toUserAddress(user);
    if (dtoUserAddress.isDefault()) {
      repository.resetDefault(user.getId());
    }
    var userAddress = repository.save(entity);
    return new DtoUserAddress(userAddress);
  }

  public DtoUserAddress updateUserAddress(DtoUserAddress dtoUserAddress) {
    var user = authService.securityContextUser();
    var entity =
        repository
            .findById(dtoUserAddress.getId())
            .orElseThrow(
                () ->
                    new ApiExceptionStatusException(
                        "unable to find id:" + dtoUserAddress.getId(), 400));
    var result = repository.save(dtoUserAddress.toUserAddress(entity, user));
    return new DtoUserAddress(result);
  }

  public void deleteUserAddress(long id) {
    var entity = repository.findById(id).orElse(null);
    if (Objects.isNull(entity)) return;
    repository.deleteById(entity.getId());
  }
}
