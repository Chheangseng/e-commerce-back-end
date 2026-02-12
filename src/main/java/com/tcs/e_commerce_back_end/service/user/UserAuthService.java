package com.tcs.e_commerce_back_end.service.user;

import com.tcs.e_commerce_back_end.emuns.Role;
import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.order.DtoMailMessage;
import com.tcs.e_commerce_back_end.model.dto.userAccount.*;
import com.tcs.e_commerce_back_end.model.dto.userAccount.jwt.JwtTokenResponse;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import com.tcs.e_commerce_back_end.model.entity.user.UserSecurity;
import com.tcs.e_commerce_back_end.repository.user.UserRepository;
import com.tcs.e_commerce_back_end.service.mail.MailService;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthService {
  private final Logger logger = LoggerFactory.getLogger(UserAuthService.class);
  private final UserRepository userRepository;
  private final MailService mailService;
  private final PasswordEncoder passwordEncoder;
  private final TokenGenerateService tokenService;

  public JwtTokenResponse loginUser(DtoUserLogin login) {
    var user = this.findByUserLogin(login);
    return tokenService.auth(this.authenticationUser(user));
  }

  public void forgotPassword(DtoUserForgotPassword dto) {
    var userAccount = findUserByEmail(dto.getEmail());
    var resetToken = UUID.randomUUID().toString();
    userAccount.setResetToken(resetToken);
    userRepository.save(userAccount);
    var mailObject = new DtoMailMessage();
    mailObject.setEmail(userAccount.getEmail());
    mailObject.setSubject("Your link forgot password");
    mailObject.setMessage("Click Here: " + dto.getCallBackUrl() + "?resetToken=" + resetToken);
    mailService.sendMail(mailObject);
  }

  public void resetPassword(DtoResetPassword dto) {
    if (Objects.isNull(dto.getResetToken())) {
      throw new ApiExceptionStatusException("Require reset token", HttpStatus.UNAUTHORIZED.value());
    }
    var entity = findResetToken(dto.getResetToken());
    entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    entity.setResetToken(null);
    userRepository.save(entity);
  }

  public void resetPasswordByAdmin(long id, DtoResetPassword dto) {
    var entity = findById(id);
    entity.setPassword(passwordEncoder.encode(dto.getPassword()));
    entity.setResetToken(null);
    userRepository.save(entity);
  }

  public JwtTokenResponse registerUser(DtoUserRegister register) {
    var entity = new UserAccount();
    entity.setEmail(register.getEmail());
    entity.setFirstName(register.getFirstName());
    entity.setLastName(register.getLastName());
    entity.setStatus(Role.USER);
    entity.setActivate(true);
    entity.setPassword(passwordEncoder.encode(register.getPassword()));
    var response = userRepository.save(entity);
    return tokenService.auth(this.authenticationUser(response));
  }

  public JwtTokenResponse refreshToken(String refreshToken) {
    var jwt = this.tokenService.verifyRefreshToken(refreshToken);
    var user = userRepository.findByUsername(jwt.getSubject()).orElse(null);
    if (Objects.isNull(user)) return null;

    return tokenService.auth(this.authenticationUser(user));
  }

  public void logout(String refreshToken) {
    try {
      var jwt = this.tokenService.verifyRefreshToken(refreshToken);
      logger.info("User " + jwt.getSubject() + " is logout");
    } catch (Exception e) {
      logger.error("error logging out {}", e);
    }
  }

  private UserAccount findResetToken(String resetToken) {
    return userRepository
        .findByResetToken(resetToken)
        .orElseThrow(() -> new ApiExceptionStatusException("invalid reset password ", 400));
  }

  public UserAccount findUserByEmail(String email) {
    return userRepository
        .findByUserEmail(email)
        .orElseThrow(
            () -> new ApiExceptionStatusException("unable to find user email: " + email, 400));
  }

  public UserAccount findByUserName(String username) {
    var user = userRepository.findByUsername(username).orElse(null);
    if (Objects.isNull(user)) {
      throw new ApiExceptionStatusException("Invalid user", HttpStatus.UNAUTHORIZED.value());
    }
    if (!user.isActivate()) {
      throw new ApiExceptionStatusException(
          "This User account have been locked", HttpStatus.UNAUTHORIZED.value());
    }
    return user;
  }

  public UserAccount findById(long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ApiExceptionStatusException("unable to find user id: " + id, 400));
  }

  public UserAccount findByUserLogin(DtoUserLogin login) {
    var user = userRepository.findByUsername(login.getEmail()).orElse(null);
    if (Objects.isNull(user)) {
      throw new ApiExceptionStatusException(
          "Invalid username or password.", HttpStatus.UNAUTHORIZED.value());
    }
    if (!user.isActivate()) {
      throw new ApiExceptionStatusException(
          "Your account have been locked.", HttpStatus.UNAUTHORIZED.value());
    }
    if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
      throw new ApiExceptionStatusException(
          "Invalid username or password.", HttpStatus.UNAUTHORIZED.value());
    }
    return user;
  }

  public Authentication authenticationUser(UserAccount user) {
    var securityUser = new UserSecurity(user);
    return new UsernamePasswordAuthenticationToken(
        securityUser, user.getPassword(), securityUser.getAuthorities());
  }

  public UserAccount securityContextUser() {
    var name = SecurityContextHolder.getContext().getAuthentication().getName();
    return this.findByUserName(name);
  }
}
