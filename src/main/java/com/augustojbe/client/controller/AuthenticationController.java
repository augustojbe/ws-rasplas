package com.augustojbe.client.controller;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

   private AuthenticationManager authenticationManager;
   private TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid LoginDto dto) {
        UsernamePasswordAuthenticationToken userPassAuth =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        try {
            Authentication auth =  authenticationManager.authenticate(userPassAuth);
            String token = tokenService.getToken(auth);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        } catch (Exception ex){
            throw new BadRequestException("Erro ao formatar token - "+ ex.getMessage());
        }
    }


}



