package com.kodegravity.paymentservice.controller;

import com.kodegravity.paymentservice.dto.RefundRequest;
import com.kodegravity.paymentservice.entity.PaymentRefund;
import com.kodegravity.paymentservice.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refunds")
public class RefundController {
    @Autowired
    private RefundService refundService;

//Initiate a REFUND
    @PostMapping("/initiate")
    public ResponseEntity<PaymentRefund>initiateRefund(@RequestBody RefundRequest refundRequest)
    {
        PaymentRefund refund=refundService.initiateRefund(refundRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(refund);
    }
//Get REFUND by ID
    @GetMapping("/{refundId}")
    public ResponseEntity<PaymentRefund>getRefundById(@PathVariable Long refundId){
        PaymentRefund refund=refundService.getRefundById(refundId);
        return ResponseEntity.ok(refund);
    }

//Get all REFUNDS for a payment

@GetMapping("/payment/{paymentId}")
public ResponseEntity<List<PaymentRefund>> getRefundsByPaymentId(@PathVariable Long paymentId){
        List<PaymentRefund>refunds=refundService.getRefundsByPaymentId(paymentId);
        return ResponseEntity.ok(refunds);
    }

//Get all REFUNDS

@GetMapping("/all")

public ResponseEntity<List<PaymentRefund>> getAllRefunds(){
        List<PaymentRefund>refunds=refundService.getAllRefunds();
        return ResponseEntity.ok(refunds);

}
//Confirm REFUND-Mark as Success
@PutMapping("/{refundId}/confirm")
    public ResponseEntity<PaymentRefund>confirmRefund(@PathVariable Long refundId){
        PaymentRefund refund= refundService.confirmRefund(refundId);
        return ResponseEntity.ok(refund);
}

//Fail REFUND-Mark as Failed
@PutMapping("/{refundId}/fail")
    public ResponseEntity<PaymentRefund>failRefund(@PathVariable Long refundId){
        PaymentRefund refund= refundService.failRefund(refundId);
        return ResponseEntity.ok(refund);
}

}
