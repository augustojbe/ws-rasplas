package com.augustojbe.client.controller;

import com.augustojbe.client.dto.PaymentProcessDto;
import com.augustojbe.client.service.PaymentInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymenteInfoController {

    private PaymentInfoService paymentInfoService;

    public PaymenteInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> process(@RequestBody PaymentProcessDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(paymentInfoService.process(dto));

    }




}
