package com.augustojbe.client.mapper.wsraspay;

import com.augustojbe.client.dto.wsraspay.CreditCardDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;

public class PaymenteMapper {

    public static PaymentDto build(String customerId, String orderId, CreditCardDto dto){

        return PaymentDto.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();

    }
}
