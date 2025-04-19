package com.augustojbe.client.mapper;

import com.augustojbe.client.dto.UserDto;
import com.augustojbe.client.model.SubscriptionType;
import com.augustojbe.client.model.User;
import com.augustojbe.client.model.UserType;

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
