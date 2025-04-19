package com.augustojbe.client.mapper;

import com.augustojbe.client.dto.SubscriptionTypeDto;
import com.augustojbe.client.model.SubscriptionType;

public class SubscriptionTypeMapper {

    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto){
        return SubscriptionType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .accessMonths(dto.getAccessMonths())
                .price(dto.getPrice())
                .productkey(dto.getProductkey())
                .build();

    }
}
