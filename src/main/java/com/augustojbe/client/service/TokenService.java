package com.augustojbe.client.service;


import org.springframework.security.core.Authentication;

public interface TokenService {

    String getToken(Authentication auth);

}
