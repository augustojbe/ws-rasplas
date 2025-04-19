package com.augustojbe.client.integration;

import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;

public interface WsRaspayIntegration {


    CustomerDto createCustomer(CustomerDto dto);

    OrderDto createOrder(OrderDto dto);

    Boolean processPayment(PaymentDto dto);

}
