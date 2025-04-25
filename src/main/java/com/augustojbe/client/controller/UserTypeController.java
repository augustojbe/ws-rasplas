package com.augustojbe.client.controller;

import com.augustojbe.client.model.jpa.UserType;
import com.augustojbe.client.repository.jpa.UserTypeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-type")
public class UserTypeController {

    private final UserTypeRepository userTypeRepository;

    public UserTypeController(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }


    @GetMapping
    public List<UserType> getAllUserTypes() {
        return userTypeRepository.findAll();
    }
}
