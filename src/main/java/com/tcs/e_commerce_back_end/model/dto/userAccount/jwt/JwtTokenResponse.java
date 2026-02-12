package com.tcs.e_commerce_back_end.model.dto.userAccount.jwt;

public record JwtTokenResponse(
    String accessToken, long expireIn, String refreshToken, long refreshTokenExpireIn) {}
