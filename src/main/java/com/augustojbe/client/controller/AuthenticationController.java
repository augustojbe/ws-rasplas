package com.augustojbe.client.controller;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.dto.TokenDto;
import com.augustojbe.client.dto.UserDetailsDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.model.redis.UserRecoveryCode;
import com.augustojbe.client.service.TokenService;
import com.augustojbe.client.service.AppUserDetailsService;
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

    private final AuthenticationManager authenticationManager;
   private final TokenService tokenService;
    private final AppUserDetailsService appUserDetailsService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    TokenService tokenService,
                                    AppUserDetailsService appUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.appUserDetailsService = appUserDetailsService;
    }
//
//    @PostMapping
//    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto) {
//        UsernamePasswordAuthenticationToken userPassAuth =
//                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
//
//        try {
//            Authentication auth = authenticationManager.authenticate(userPassAuth);
//            String token = tokenService.getToken(auth);
//            return ResponseEntity.status(HttpStatus.OK).body(token);
//        } catch (Exception ex) {
//            throw new BadRequestException("Erro ao formatar token - " + ex.getMessage());
//        }
//    }


    @PostMapping("/recovery-code/send")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode dto) {
        appUserDetailsService.sendRecoveryCode(dto.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/recovery-code/")
    public ResponseEntity<?> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,
                                                 @RequestParam("email") String email) {

        return ResponseEntity.status(HttpStatus.OK).body(appUserDetailsService.recoveryCodeIsValid(recoveryCode, email));
    }

    @PatchMapping("/recovery-code/password")
    public ResponseEntity<?> updatePasswordRecoveryCode(@RequestBody @Valid UserDetailsDto dto) {
        appUserDetailsService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }




}



