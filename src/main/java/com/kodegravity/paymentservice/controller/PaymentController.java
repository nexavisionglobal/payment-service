package com.kodegravity.paymentservice.controller;


import com.kodegravity.paymentservice.dto.PaymentRequest;
import com.kodegravity.paymentservice.dto.PaymentResponse;
import com.kodegravity.paymentservice.entity.Payment;
import com.kodegravity.paymentservice.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController

@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
  //START PAYMENT
    @PostMapping("/initiate")
    public Payment initiatePayment
    (@RequestBody PaymentRequest request) {
      return paymentService.initiatePayment(request);}

  //PROCESS PAYMENT RESULT
  @PostMapping("/process")
  public Payment processPayment(@RequestBody Payment payment) {
      return paymentService.processPayment(payment);
  }
    //GET ALL PAYMENTS
    @GetMapping
  public List<Payment> getAllPayments() {
    return paymentService.getAllPayments();}

    //GET PAYMENTS BY id
  @GetMapping("/{id}")
   public Payment getPaymentById(@PathVariable Long id){
      return paymentService.getPaymentById(id);
  }

}
