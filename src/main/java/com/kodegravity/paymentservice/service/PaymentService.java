package com.kodegravity.paymentservice.service;

import com.kodegravity.paymentservice.dto.PaymentRequest;
import com.kodegravity.paymentservice.entity.Payment;
import com.kodegravity.paymentservice.entity.PaymentMethod;
import com.kodegravity.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }
//INITIATE PAYMENT
    public Payment initiatePayment(PaymentRequest paymentRequest) {
        Payment payment = new Payment();
        payment.setOrderId(paymentRequest.getOrderId());
        payment.setUserId(paymentRequest.getUserId());
        payment.setAmount(paymentRequest.getAmount());
        payment.setCurrency(paymentRequest.getCurrency());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        payment.setPaymentStatus(com.kodegravity.paymentservice.entity.PaymentStatus.PENDING);
        return paymentRepository.save(payment);
    }
//PROCESS PAYMENT
    public Payment processPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
//GET ALL PAYMENTS
    public java.util.List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
//GET PAYMENT BY ID
    public Payment getPaymentById(Long id){
        return paymentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Payment not found: " + id));

}

}

