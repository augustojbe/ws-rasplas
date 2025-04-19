package com.augustojbe.client.integration.impl;

import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.dto.wsraspay.OrderDto;
import com.augustojbe.client.dto.wsraspay.PaymentDto;
import com.augustojbe.client.integration.WsRaspayIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

//    @Value("${webservices.raspay.host}")
//    private String raspayHost;
//
//    @Value("${webservices.raspay.v1.customer}")
//    private String customerUrl;
//
//    @Value("${webservices.raspay.v1.order}")
//    private String orderUrl;
//
//    @Value("${webservices.raspay.v1.payment}")
//    private String paymentUrl;


    private RestTemplate restTemplate;

    public WsRaspayIntegrationImpl() {
       restTemplate = new RestTemplate();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            var headers = getHttpHeaders();
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<CustomerDto> response =
                    restTemplate.exchange("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/customer",
                            HttpMethod.POST,
                            request,
                            CustomerDto.class );
            return response.getBody();
        } catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {

        try {
            var headers = getHttpHeaders();
            HttpEntity<OrderDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<OrderDto> response =
                    restTemplate.exchange("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/order",
                            HttpMethod.POST,
                            request,
                            OrderDto.class );
            return response.getBody();
        } catch (Exception ex){
            throw ex;
        }

    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        try {
            var headers = getHttpHeaders();
            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, headers);
            ResponseEntity<Boolean> response =
                    restTemplate.exchange("https://raspay-api-61f5fa5fc34c.herokuapp.com/ws-raspay/v1/payment/credit-card/",
                            HttpMethod.POST,
                            request,
                            Boolean.class );
            return response.getBody();
        } catch (Exception ex){
            throw ex;
        }

    }

    private static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";
        String base64 = Base64.getEncoder().encodeToString(credential.getBytes());
        headers.add("Authorization", "Basic "+ base64);
        return headers;
    }

}
