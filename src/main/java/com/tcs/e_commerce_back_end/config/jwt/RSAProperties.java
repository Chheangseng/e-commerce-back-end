package com.tcs.e_commerce_back_end.config.jwt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rsa-key")
public record RSAProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {}

