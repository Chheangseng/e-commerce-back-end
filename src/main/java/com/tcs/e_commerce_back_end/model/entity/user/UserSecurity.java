package com.tcs.e_commerce_back_end.model.entity.user;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurity implements UserDetails {
  private final UserAccount userAccount;

  public UserSecurity(UserAccount userAccount) {
    this.userAccount = userAccount;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.userAccount.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
        .collect(Collectors.toSet());
  }

  @Override
  public String getPassword() {
    return this.userAccount.getPassword();
  }

  @Override
  public String getUsername() {
    return this.userAccount.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.userAccount.isActivate();
  }

  @Override
  public boolean isEnabled() {
    return this.userAccount.isActivate();
  }

  public static Optional<UserSecurity> getUserSecurityContext() {
    return UserSecurity.getUserSecurityBYAuthentication(
        SecurityContextHolder.getContext().getAuthentication());
  }

  public static Optional<UserSecurity> getUserSecurityBYAuthentication(
      Authentication authentication) {
    if (authentication != null && authentication.getPrincipal() instanceof UserSecurity) {
      return Optional.of((UserSecurity) authentication.getPrincipal());
    }
    return Optional.empty(); // or throw exception if required
  }
}
