package com.augustojbe.client.mapper.wsraspay;

import com.augustojbe.client.dto.wsraspay.CustomerDto;
import com.augustojbe.client.model.jpa.User;

public class CustomerMapper {

    public static CustomerDto build(User user){

        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[fullName.length - 1] : "";

        return CustomerDto.builder()
                .email(user.getEmail())
                .cpf(user.getCpf())
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
