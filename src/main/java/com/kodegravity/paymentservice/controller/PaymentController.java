package com.kodegravity.paymentservice.controller;

import com.kodegravity.paymentservice.dto.PaymentRequest;
import com.kodegravity.paymentservice.dto.PaymentResponse;
import com.kodegravity.paymentservice.entity.Payment;
import com.kodegravity.paymentservice.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Value("${stripe.publishable.key:}")
    private String stripePublishableKey;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // GET STRIPE PUBLIC KEY
    @GetMapping("/stripe-key")
    public Map<String, String> getStripeKey() {
        Map<String, String> response = new HashMap<>();
        response.put("publishableKey", stripePublishableKey);
        return response;
    }

    // DEBUG ENDPOINT
    @GetMapping("/debug")
    public Map<String, String> debug() {
        Map<String, String> response = new HashMap<>();
        response.put("stripePublishableKey", stripePublishableKey != null && !stripePublishableKey.isEmpty() ? stripePublishableKey : "NULL - KEY NOT LOADED");
        response.put("message", "Check if stripe.publishable.key is in application.properties");
        return response;
    }

    // START PAYMENT
    @PostMapping("/initiate")
    public Payment initiatePayment(@RequestBody PaymentRequest request) throws StripeException {
        return paymentService.initiatePayment(request);
    }

    // PROCESS PAYMENT RESULT
    @PostMapping("/process")
    public Payment processPayment(@RequestBody Payment payment) {
        return paymentService.processPayment(payment);
    }

    // GET ALL PAYMENTS
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // GET PAYMENTS BY ID
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }

    // CONFIRM PAYMENT
    @PostMapping("/{id}/confirm")
    public Payment confirmPayment(@PathVariable Long id) throws StripeException {
        return paymentService.confirmPayment(id);
    }

    // FAIL PAYMENT
    @PostMapping("/{id}/fail")
    public Payment failPayment(@PathVariable Long id) {
        return paymentService.failPayment(id);
    }

    // CANCEL PAYMENT
    @PostMapping("/{id}/cancel")
    public Payment cancelPayment(@PathVariable Long id) {
        return paymentService.cancelPayment(id);
    }
}
