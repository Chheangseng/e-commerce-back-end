package com.tcs.e_commerce_back_end.config.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin-properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemAdminProperty {
    String email;
    String firstName;
    String lastName;
    String password;
}
