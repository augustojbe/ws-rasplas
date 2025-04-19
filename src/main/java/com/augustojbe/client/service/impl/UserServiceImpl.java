package com.augustojbe.client.service.impl;

import com.augustojbe.client.dto.UserDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.mapper.UserMapper;
import com.augustojbe.client.model.User;
import com.augustojbe.client.model.UserType;
import com.augustojbe.client.repository.UserRepository;
import com.augustojbe.client.repository.UserTypeRepository;
import com.augustojbe.client.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }


    @Override
    public User create(UserDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("id deve ser nulo");
        }

        var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());

        if (userTypeOpt.isEmpty()) {
            throw new NotFoundException("userType não encontrado");
        }

        UserType userType = userTypeOpt.get();
        User user = UserMapper.fromDtoToEntity(dto, userType, null);

        return userRepository.save(user);
    }

}
