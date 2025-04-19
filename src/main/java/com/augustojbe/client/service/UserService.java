package com.augustojbe.client.service;

import com.augustojbe.client.dto.UserDto;
import com.augustojbe.client.model.User;

public interface UserService {

    User create(UserDto dto);
}
