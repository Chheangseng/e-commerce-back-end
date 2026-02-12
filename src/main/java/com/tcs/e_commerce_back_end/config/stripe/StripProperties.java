package com.tcs.e_commerce_back_end.config.stripe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.strip-properties")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StripProperties {
    private String publicKey;
    private String secretKey;
}
