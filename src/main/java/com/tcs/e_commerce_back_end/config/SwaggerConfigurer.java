package com.tcs.e_commerce_back_end.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfigurer {
  protected GroupedOpenApi groupedOpenApi(String group, String... pathToMatch) {
    return GroupedOpenApi.builder().group(group).pathsToMatch(pathToMatch).build();
  }

  @Bean
  GroupedOpenApi authentication() {
    return groupedOpenApi("Authentication", "/**/auth/**");
  }

  @Bean
  GroupedOpenApi categoryApi() {
    return groupedOpenApi("Category", "/**/categories/**");
  }

  @Bean
  GroupedOpenApi productApi() {
    return groupedOpenApi("Products", "/**/products/**");
  }
  @Bean
  GroupedOpenApi rateAPI() {
    return groupedOpenApi("Review", "/**/review/**");
  }
  @Bean
  GroupedOpenApi orderApi() {
    return groupedOpenApi("Order", "/**/order/**");
  }
  @Bean
  GroupedOpenApi userManagement() {
    return groupedOpenApi("user-management", "/**/v1/user-management/**");
  }
  @Bean
  GroupedOpenApi userAddress() {
    return groupedOpenApi("user-address", "/**/v1/user-address/**");
  }
  @Bean
  GroupedOpenApi migrate() {
    return groupedOpenApi("migrate-system", "/**/migrate/**");
  }
}
