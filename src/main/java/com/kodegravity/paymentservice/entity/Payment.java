package com.kodegravity.paymentservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name ="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String  paymentId;
    private String orderId;
    private Integer userId;
    private Double amount;
    private String currency;
    private String gatewayOrderId;
    private String gatewayPaymentId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;


   @Enumerated(EnumType.STRING)
   private  PaymentStatus paymentStatus;


    public void setOrderId(Long orderId) {
    }
}
