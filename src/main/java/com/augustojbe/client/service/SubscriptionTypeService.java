package com.augustojbe.client.service;

import com.augustojbe.client.dto.SubscriptionTypeDto;
import com.augustojbe.client.model.jpa.SubscriptionType;

import java.util.List;

public interface SubscriptionTypeService {

    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDto dto);

    SubscriptionType update(Long id, SubscriptionTypeDto dto);

    void delete(Long id);


}
