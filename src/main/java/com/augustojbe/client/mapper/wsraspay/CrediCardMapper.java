package com.augustojbe.client.mapper.wsraspay;

import com.augustojbe.client.dto.UserPaymentInfoDto;
import com.augustojbe.client.dto.wsraspay.CreditCardDto;

public class CrediCardMapper {

    public static CreditCardDto build(UserPaymentInfoDto dto, String documentNumber){
        return CreditCardDto.builder()
                .documentNumber(documentNumber)
                .cvv(Long.parseLong(dto.getCardSecurityCode()))
                .number(dto.getCardNumber())
                .month(dto.getCardExpirationMonth())
                .year(dto.getCardExpirationYear())
                .installments(dto.getInstallments())
                .build();
    }
}
