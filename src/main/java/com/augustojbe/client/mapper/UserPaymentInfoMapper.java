package com.augustojbe.client.mapper;

import com.augustojbe.client.dto.UserPaymentInfoDto;
import com.augustojbe.client.model.User;
import com.augustojbe.client.model.UserPaymenteInfo;

public class UserPaymentInfoMapper {

    public static UserPaymenteInfo fromDtoToEntity(UserPaymentInfoDto dto, User user){
        return UserPaymenteInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtPayment(dto.getDtPayment())
                .installments(dto.getInstallments())
                .user(user)
                .build();

    }
}
