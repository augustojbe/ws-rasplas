package com.augustojbe.client.controller;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.model.UserCredential;
import com.augustojbe.client.repository.UseDetailRepository;
import com.augustojbe.client.repository.UserTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserCredencialController {

    private final UseDetailRepository repository;
    private final PasswordEncoder encoder;

    public UserCredencialController(UseDetailRepository repository,
                                    PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody LoginDto dto) {

        UserCredential user = new UserCredential();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));

        repository.save(user);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
