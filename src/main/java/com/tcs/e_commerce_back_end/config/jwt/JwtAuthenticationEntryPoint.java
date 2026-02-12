package com.tcs.e_commerce_back_end.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    handleException(response, authException.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    handleException(response, accessDeniedException.getMessage(), HttpServletResponse.SC_FORBIDDEN);
  }

  private void handleException(HttpServletResponse response, String message, int status)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(status);

    Map<String, Object> body = new HashMap<>();
    body.put("status", status);
    body.put("message", message);
    body.put("path", "Check your security config"); // Optional extra info

    objectMapper.writeValue(response.getOutputStream(), body);
  }
}
