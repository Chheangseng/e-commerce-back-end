package com.tcs.e_commerce_back_end.config;

import com.tcs.e_commerce_back_end.config.jwt.AuthenticationJwtConverter;
import com.tcs.e_commerce_back_end.config.jwt.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtAuthenticationEntryPoint authenticationEntryPoint;
  private final AuthenticationJwtConverter converter;

  public SecurityConfig(
      JwtAuthenticationEntryPoint authenticationEntryPoint, AuthenticationJwtConverter converter) {
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.converter = converter;
  }
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
      }
    };
  }

  @Bean
  public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .httpBasic(Customizer.withDefaults())
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/v1/**").authenticated().anyRequest().permitAll())
        .oauth2ResourceServer(
            server ->
                server
                    .authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(authenticationEntryPoint)
                    .jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(converter))
        )
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
