package com.tcs.e_commerce_back_end.service.user;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.userAccount.jwt.JwtTokenResponse;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenGenerateService {
  private final String issuer = "authentication-server";
  private final JwtEncoder encoder;
  private final JwtDecoder decoder;
  // 3 min
  private final long expireInSeconds = 120;
  //  15 min
  private final long refreshTokenExpireInSeconds = 900;

  public JwtTokenResponse auth(Authentication authentication) {
    Instant now = Instant.now();
    return new JwtTokenResponse(
        accessToken(authentication, now),
        expireInSeconds,
        refreshToken(authentication, now),
        refreshTokenExpireInSeconds);
  }

  private String accessToken(Authentication authentication, Instant now) {
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expireInSeconds))
            .subject(authentication.getName())
            .claim("scope", this.getScope(authentication))
            .claim("roles", this.getRoles(authentication))
            .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  private String refreshToken(Authentication authentication, Instant now) {
    JwtClaimsSet claims =
        JwtClaimsSet.builder()
            .issuer(issuer)
            .issuedAt(now)
            .expiresAt(now.plusSeconds(refreshTokenExpireInSeconds))
            .subject(authentication.getName())
            .claim("type", "refreshToken")
            .build();
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  public List<String> getRoles(Authentication authentication) {

    return authentication.getAuthorities().stream()
        .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("ROLE_"))
        .map(auth -> auth.getAuthority().substring(5))
        .collect(Collectors.toList());
  }

  public List<String> getScope(Authentication authentication) {
    return authentication.getAuthorities().stream()
        .filter(grantedAuthority -> grantedAuthority.getAuthority().startsWith("SCOPE_"))
        .map(auth -> auth.getAuthority().substring(6))
        .collect(Collectors.toList());
  }

  public Jwt verifyRefreshToken(String refreshToken) {
    try {
      var decode = decoder.decode(refreshToken);
      var type = decode.getClaim("type");
      if (Objects.isNull(type) || !type.toString().equals("refreshToken")) {
        throw new ApiExceptionStatusException("Incorrect Token Type or format", 400);
      }
      return decode;
    } catch (JwtException e) {
      throw new ApiExceptionStatusException(e.getMessage(), 400, e);
    }
  }
}
