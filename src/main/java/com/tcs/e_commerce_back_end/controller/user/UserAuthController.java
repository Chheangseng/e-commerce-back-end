package com.tcs.e_commerce_back_end.controller.user;

import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoResetPassword;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoUserForgotPassword;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoUserLogin;
import com.tcs.e_commerce_back_end.model.dto.userAccount.DtoUserRegister;
import com.tcs.e_commerce_back_end.model.dto.userAccount.jwt.JwtTokenResponse;
import com.tcs.e_commerce_back_end.service.user.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
  private final UserAuthService service;

  public UserAuthController(UserAuthService service) {
    this.service = service;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtTokenResponse> login(@RequestBody @Valid DtoUserLogin login) {
    return ResponseEntity.ok(service.loginUser(login));
  }

  @PostMapping("/register")
  public ResponseEntity<JwtTokenResponse> register(@RequestBody @Valid DtoUserRegister register) {
    return ResponseEntity.ok(service.registerUser(register));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<Void> forgotPassword(@RequestBody @Valid DtoUserForgotPassword dto) {
    service.forgotPassword(dto);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/reset-password")
  public ResponseEntity<Void> resetPassword(@RequestBody DtoResetPassword resetPassword) {
    service.resetPassword(resetPassword);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<JwtTokenResponse> refreshToken(@RequestParam String refreshToken) {
    return ResponseEntity.ok(service.refreshToken(refreshToken));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@RequestParam String refreshToken) {
    service.logout(refreshToken);
    return ResponseEntity.ok().build();
  }
}
