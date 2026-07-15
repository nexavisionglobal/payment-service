package com.kodegravity.paymentservice.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private Long orderId;
    private Integer userId;
    private Double amount;
    private String currency;
    private String paymentMethod;

}
