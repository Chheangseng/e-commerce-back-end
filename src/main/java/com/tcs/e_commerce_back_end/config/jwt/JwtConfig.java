package com.tcs.e_commerce_back_end.config.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration
public class JwtConfig {
  private final RSAProperties rsaProperties;

  public JwtConfig(RSAProperties rsaProperties) {
    this.rsaProperties = rsaProperties;
  }

  @Bean
  public JwtDecoder decoder() {
    return NimbusJwtDecoder.withPublicKey(rsaProperties.publicKey()).build();
  }

  @Bean
  public JwtEncoder encoder() {
    JWK jwk =
        new RSAKey.Builder(rsaProperties.publicKey())
            .privateKey(rsaProperties.privateKey())
            .build();
    return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
  }

  @Bean
  JWKSet jwkSet() {
    RSAKey.Builder builder =
        new RSAKey.Builder(rsaProperties.publicKey())
            .keyUse(KeyUse.SIGNATURE)
            .keyID("public-key-id")
            .algorithm(JWSAlgorithm.RS256);
    return new JWKSet(builder.build());
  }
}
