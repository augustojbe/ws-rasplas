package com.augustojbe.client.service.impl;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.dto.TokenDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.service.AuthenticationService;
import com.augustojbe.client.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class EuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public EuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public TokenDto auth(LoginDto dto) {
        try {
            UsernamePasswordAuthenticationToken userPassAuth =
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

            Authentication auth = authenticationManager.authenticate(userPassAuth);
            String token = tokenService.getToken(auth);
            return TokenDto.builder().token(token).type("Bearer").build();
        } catch (Exception ex){
            throw new BadRequestException("Erro ao formatar token - "+ ex.getMessage());
        }


    }
}
