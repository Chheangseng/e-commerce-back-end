package com.tcs.e_commerce_back_end.config.stripe;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {
    private final StripProperties properties;

    public StripeConfig(StripProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init(){
        Stripe.apiKey = properties.getSecretKey();
    }
}
