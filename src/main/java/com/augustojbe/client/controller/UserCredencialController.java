package com.augustojbe.client.controller;

import com.augustojbe.client.dto.LoginDto;
import com.augustojbe.client.model.UserCredential;
import com.augustojbe.client.model.UserType;
import com.augustojbe.client.repository.UseDetailRepository;
import com.augustojbe.client.repository.UserTypeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserCredencialController {

    private final UseDetailRepository repository;
    private final PasswordEncoder encoder;
    private final UserTypeRepository userTypeRepository;

    public UserCredencialController(UseDetailRepository repository,
                                    PasswordEncoder encoder, UserTypeRepository userTypeRepository) {
        this.repository = repository;
        this.encoder = encoder;
        this.userTypeRepository = userTypeRepository;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody LoginDto dto) {

        // Busca o tipo de usuário com base em um campo adequado, como 'role' ou 'name'
        Optional<UserType> optionalUserType = userTypeRepository.findByName(dto.getUsername());

        UserType userType = optionalUserType.get();

        // Criação do usuário
        UserCredential user = new UserCredential();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setUserType(userType);

        // Salvar o usuário no repositório
        repository.save(user);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
