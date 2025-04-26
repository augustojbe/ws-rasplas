package com.augustojbe.client.service;

import com.augustojbe.client.dto.UserDto;
import com.augustojbe.client.model.jpa.User;
import com.augustojbe.client.model.jpa.UserType;
import com.augustojbe.client.repository.jpa.UserRepository;
import com.augustojbe.client.repository.jpa.UserTypeRepository;
import com.augustojbe.client.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void give_create_when_idIsNotNullAndUserIsFound_then_returnUserCreated(){

        UserDto dto = new UserDto();
        dto.setEmail("augustojbe@gmail.com");
        dto.setCpf("02677774348");
        dto.setUserTypeId(1L);

        UserType userType = new UserType(1L, "Aluno", "Aluno da plataforma");

        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setCpf(dto.getCpf());
        user.setDtSubscription(dto.getDtSubscription());
        user.setDtExpirtion(dto.getDtExpirtion());
        user.setUserType(userType);
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(user, userService.create(dto));

        verify(userTypeRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);

    }

}