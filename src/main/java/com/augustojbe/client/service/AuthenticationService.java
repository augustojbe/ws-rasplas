package com.augustojbe.client.service;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.dto.TokenDto;

public interface AuthenticationService {

    TokenDto auth(LoginDto dto);
}
