package com.kodegravity.paymentservice.service;

import com.kodegravity.paymentservice.dto.PaymentRequest;
import com.kodegravity.paymentservice.entity.Payment;
import com.kodegravity.paymentservice.entity.PaymentMethod;
import com.kodegravity.paymentservice.entity.PaymentStatus;
import com.kodegravity.paymentservice.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StripeService stripeService;

    public PaymentService(PaymentRepository paymentRepository, StripeService stripeService) {
        this.paymentRepository = paymentRepository;
        this.stripeService = stripeService;
    }

    // INITIATE PAYMENT
    public Payment initiatePayment(PaymentRequest paymentRequest) throws StripeException {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setUserId(paymentRequest.getUserId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        payment.setPaymentStatus(PaymentStatus.PENDING);
        
        // Create Stripe Payment Intent
        String paymentIntentId = stripeService.createPaymentIntent(
            paymentRequest.getAmount(),
            paymentRequest.getCurrency()
        );
        payment.setStripePaymentIntentId(paymentIntentId);
        
        return paymentRepository.save(payment);
    }

    // PROCESS PAYMENT
    public Payment processPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // GET ALL PAYMENTS
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // GET PAYMENT BY ID
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Payment not found: " + id));
    }

    // CONFIRM PAYMENT
    public Payment confirmPayment(Long id) throws StripeException {
        Payment payment = getPaymentById(id);
        
        if (payment.getStripePaymentIntentId() != null) {
            stripeService.confirmPaymentIntent(payment.getStripePaymentIntentId());
        }
        
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        return paymentRepository.save(payment);
    }

    // FAIL PAYMENT
    public Payment failPayment(Long id) {
        Payment payment = getPaymentById(id);
        payment.setPaymentStatus(PaymentStatus.FAILED);
        return paymentRepository.save(payment);
    }

    // CANCEL PAYMENT
    public Payment cancelPayment(Long id) {
        Payment payment = getPaymentById(id);
        payment.setPaymentStatus(PaymentStatus.CANCELLED);
        return paymentRepository.save(payment);
    }
}
