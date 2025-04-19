package com.augustojbe.client.integration;

import com.augustojbe.client.dto.wsraspay.CreditCardDto;
import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOk(){
        CustomerDto dto = new CustomerDto(null, "71754048080", "esther@teste.com", "Esther","Mario");
        wsRaspayIntegration.createCustomer(dto);

    }

    @Test
    void createOrderWhenDtoOk(){
        OrderDto dto = new OrderDto(null,"680237748c6ebb5c709f57c6", BigDecimal.ZERO,"YEAR23");
        wsRaspayIntegration.createOrder(dto);

    }

    @Test
    void processPaymentWhenDtoOk(){
        CreditCardDto creditCardDto = new CreditCardDto(828L, "71754048080", 0L, 7L, "5335647167301608", 2027L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, "680237748c6ebb5c709f57c6", "680238908c6ebb5c709f57c8");
        wsRaspayIntegration.processPayment(paymentDto);

    }
}

