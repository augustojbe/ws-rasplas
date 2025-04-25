package com.augustojbe.client.mapper;

import com.augustojbe.client.dto.UserDto;
import com.augustojbe.client.model.jpa.SubscriptionType;
import com.augustojbe.client.model.jpa.User;
import com.augustojbe.client.model.jpa.UserType;

public class UserMapper {

    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .dtSubscription(dto.getDtSubscription())
                .dtExpirtion(dto.getDtExpirtion())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();

    }
}
