package com.augustojbe.client.integration;

import com.augustojbe.client.dto.wsraspay.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailIntegrationImplTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void createCustomerWhenDtoOk(){
        mailIntegration.send("doroteiastore4748@gmail.com", "Olá Gmail", "Acesso Liberado");

    }

}

