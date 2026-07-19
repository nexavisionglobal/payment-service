package com.kodegravity.paymentservice.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.api.key:}")
    private String stripeApiKey;

    // CREATE PAYMENT INTENT
    public String createPaymentIntent(Long amount, String currency) throws StripeException {
        if (stripeApiKey == null || stripeApiKey.isEmpty()) {
            throw new StripeException("Stripe API key not configured");
        }
        Stripe.apiKey = stripeApiKey;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .build();

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getId();
    }

    // CONFIRM PAYMENT INTENT
    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        if (stripeApiKey == null || stripeApiKey.isEmpty()) {
            throw new StripeException("Stripe API key not configured");
        }
        Stripe.apiKey = stripeApiKey;
        return PaymentIntent.retrieve(paymentIntentId);
    }
}
