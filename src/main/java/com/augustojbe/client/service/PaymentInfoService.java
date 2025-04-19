package com.augustojbe.client.service;

import com.augustojbe.client.dto.PaymentProcessDto;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDto dto);
}
