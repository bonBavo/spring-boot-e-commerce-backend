package com.bonbravo.store.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret}")
    private String secretKey;

    @PostConstruct
    public void initializeStripe() {
        // Initialize Stripe with the secret key
        Stripe.apiKey = secretKey;
    }

}
