package com.kodegravity.paymentservice.dto;

import com.kodegravity.paymentservice.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String paymentId;
    private PaymentStatus paymentStatus;
    private String message;



}
