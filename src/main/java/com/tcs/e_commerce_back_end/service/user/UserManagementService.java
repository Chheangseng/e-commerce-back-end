package com.tcs.e_commerce_back_end.service.user;

import com.tcs.e_commerce_back_end.config.admin.SystemAdminProperty;
import com.tcs.e_commerce_back_end.emuns.AddressType;
import com.tcs.e_commerce_back_end.emuns.Role;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoAccountList;
import com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent.DtoUserAccountParent;
import com.tcs.e_commerce_back_end.model.entity.order.Orders;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import com.tcs.e_commerce_back_end.model.entity.user.UserAddress;
import com.tcs.e_commerce_back_end.repository.order.OrderRepository;
import com.tcs.e_commerce_back_end.repository.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserManagementService {
  private final PasswordEncoder encoder;
  private final UserRepository userRepository;
  private final UserAuthService userAuthService;
  private final SystemAdminProperty adminProperty;
  private final OrderRepository orderRepository;


  public List<DtoAccountList> listingUser(String search) {
    var entityList = userRepository.filterUserAccount(ObjectUtils.getIfNull(search, ""));
    var responseDto = new ArrayList<DtoAccountList>();
    for (var entity : entityList) {
      List<Orders> ordersUser = orderRepository.findAllByUserAccountId(entity.getId());
      var dtoItem = new DtoAccountList(entity);
      dtoItem.setTotalOrder(ordersUser.size());
      responseDto.add(dtoItem);
    }
    return responseDto;
  }

  public DtoUserAccountParent viewDetail(long id) {
    var entity = userAuthService.findById(id);
    return new DtoUserAccountParent(entity);
  }

  public void deleteUser(long id) {
    var userAccount = userRepository.findById(id).orElse(null);
    if (Objects.nonNull(userAccount)) {
      userRepository.deleteById(userAccount.getId());
    }
  }

  public DtoUserAccountParent viewDetailByToken(String username) {
    var entity = userAuthService.findByUserName(username);
    return new DtoUserAccountParent(entity);
  }

  public void editUserByAdmin(DtoUserAccountParent dtoUserAccountParent) {
    var user = this.userAuthService.findById(dtoUserAccountParent.getId());
    dtoUserAccountParent.toUserAccount(user);
    userRepository.save(user);
  }

  public void editUserByUser(
      DtoUserAccountParent dtoUserAccountParent, Authentication authentication) {
    var user = this.userAuthService.findByUserName(authentication.getName());
    dtoUserAccountParent.toUserAccount(user);
    userRepository.save(user);
  }

  public void userActivation(long id, boolean isActivate) {
    var userAccount = userAuthService.findById(id);
    userAccount.setActivate(isActivate);
    userRepository.save(userAccount);
  }

  public void insertDefaultAdmin() {
    this.insertDefaultAdminDataBase();
  }

  public void insertDefaultTestingUser() {
    this.insertDefaultTestingUserDataBase();
  }

  public void insertDefaultAdminDataBase() {
    var user = userRepository.findByUserEmail(adminProperty.getEmail()).orElse(null);
    if (Objects.nonNull(user)) return;
    var adminAccountDB = new UserAccount();
    adminAccountDB.setEmail(adminProperty.getEmail());
    adminAccountDB.setUsername(adminProperty.getEmail());
    adminAccountDB.setFirstName(adminProperty.getFirstName());
    adminAccountDB.setLastName(adminProperty.getLastName());
    adminAccountDB.setPassword(encoder.encode(adminProperty.getPassword()));
    adminAccountDB.getRoles().add(Role.ADMIN.getValue());
    adminAccountDB.setActivate(true);
    userRepository.save(adminAccountDB);
  }

  public void insertDefaultTestingUserDataBase() {
    var email = "taingchheangseng123@gmail.com";
    var user = userRepository.findByUserEmail(email).orElse(null);
    if (Objects.nonNull(user)) return;
    var testAccount = new UserAccount();
    testAccount.setUsername(email);
    testAccount.setEmail(email);
    testAccount.setFirstName("chheang");
    testAccount.setLastName("seng");
    testAccount.setPassword(encoder.encode("123"));
    testAccount.getRoles().add(Role.USER.getValue());
    testAccount.setActivate(true);
    this.addTestingUserAddress(testAccount);
    userRepository.save(testAccount);
  }

  private void addTestingUserAddress(UserAccount entity) {
    var address = new UserAddress();
    address.setFullName(entity.getFirstName() + ' ' + entity.getLastName());
    address.setUserAccount(entity);
    address.setEmail(entity.getEmail());
    address.setPhoneNumber("+855");
    address.setAddressDetail("test address detail");
    address.setAddress("test address");
    address.setCity("test city");
    address.setDefault(true);
    address.setType(AddressType.HOME);
    entity.getUserAddresses().add(address);
  }
}
