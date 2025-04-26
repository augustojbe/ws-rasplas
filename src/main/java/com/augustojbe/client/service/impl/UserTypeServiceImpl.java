package com.augustojbe.client.service.impl;

import com.augustojbe.client.model.jpa.UserType;
import com.augustojbe.client.repository.jpa.UserTypeRepository;
import com.augustojbe.client.service.UserTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {

    private final UserTypeRepository userTypeRepository;

    public UserTypeServiceImpl(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
