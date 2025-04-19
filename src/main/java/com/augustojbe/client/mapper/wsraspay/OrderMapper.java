package com.augustojbe.client.mapper.wsraspay;

import com.augustojbe.client.dto.PaymentProcessDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;

public class OrderMapper {

    public static OrderDto build(String customerId, PaymentProcessDto paymentProcessDto){
        return OrderDto.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDto.getProductKey())
                .discount(paymentProcessDto.getDescount())
                .build();

    }
}
