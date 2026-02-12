package com.tcs.e_commerce_back_end.controller.user;

import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoAccountList;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoResetPassword;
import com.tcs.e_commerce_back_end.model.dto.userAccount.dtoParent.DtoUserAccountParent;
import com.tcs.e_commerce_back_end.service.user.UserAuthService;
import com.tcs.e_commerce_back_end.service.user.UserManagementService;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user-management")
public class UserManagementController {
  private final UserManagementService service;
  private final UserAuthService userAuthService;

  public UserManagementController(UserManagementService service, UserAuthService userAuthService) {
    this.service = service;
      this.userAuthService = userAuthService;
  }

  @GetMapping
  @PreAuthorize("hasRole('SUPER-ADMIN')")
  public ResponseEntity<List<DtoAccountList>> listUser(
      @RequestParam(required = false) String search) {
    return ResponseEntity.ok(service.listingUser(search));
  }
  @PutMapping("/reset-password")
  public ResponseEntity<Void> resetPasswordWithToken(@RequestBody DtoResetPassword resetPassword){
    userAuthService.resetPassword(resetPassword);
    return ResponseEntity.ok().build();
  }
  @PutMapping("/reset-password/{id}")
  public ResponseEntity<Void> resetPasswordWithToken(@PathVariable Long id,@RequestBody DtoResetPassword resetPassword){
    userAuthService.resetPasswordByAdmin(id,resetPassword);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{id}")
  public ResponseEntity<DtoUserAccountParent> listProduct(@PathVariable Long id) {
    return ResponseEntity.ok(service.viewDetail(id));
  }
  @GetMapping("/profile")
  public ResponseEntity<DtoUserAccountParent> userProfile(Authentication authentication) {
    return ResponseEntity.ok(service.viewDetailByToken(authentication.getName()));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('SUPER-ADMIN')")
  public ResponseEntity<Void> editUser(
      @PathVariable Long id, @RequestBody DtoUserAccountParent dtoUserAccountParent) {
    if (Objects.isNull(dtoUserAccountParent.getId())) {
      dtoUserAccountParent.setId(id);
    }
    service.editUserByAdmin(dtoUserAccountParent);
    return ResponseEntity.ok().build();
  }
  @PutMapping
  public ResponseEntity<Void> editUser(
        @RequestBody DtoUserAccountParent dtoUserAccountParent,Authentication authentication) {
    service.editUserByUser(dtoUserAccountParent,authentication);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    service.deleteUser(id);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{id}/active")
  @PreAuthorize("hasRole('SUPER-ADMIN')")
  public ResponseEntity<Void> activateUser(@PathVariable Long id, @RequestParam Boolean active) {
    service.userActivation(id, active);
    return ResponseEntity.ok().build();
  }
}
