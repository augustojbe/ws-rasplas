package com.augustojbe.client.service.impl;

import com.augustojbe.client.dto.UserDetailsDto;
import com.augustojbe.client.exception.BadRequestException;
import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.integration.MailIntegration;
import com.augustojbe.client.model.jpa.UserCredential;
import com.augustojbe.client.model.redis.UserRecoveryCode;
import com.augustojbe.client.repository.jpa.UseDetailRepository;
import com.augustojbe.client.repository.redis.UserRecoveryCodeRepository;
import com.augustojbe.client.service.AppUserDetailsService;
import com.augustojbe.client.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class AppUserDetailsServiceImpl implements AppUserDetailsService {

    @Value("${webservices.rasplus.redis.recoverycode.timeout}")
    private String recoveryCodeTimeout;

    private UseDetailRepository useDetailRepository;
    private final UserRecoveryCodeRepository userRecoveryCodeRepository;
    private final MailIntegration mailIntegration;

    public AppUserDetailsServiceImpl(UseDetailRepository useDetailRepository,
                                     UserRecoveryCodeRepository userRecoveryCodeRepository,
                                     MailIntegration mailIntegration) {
        this.useDetailRepository = useDetailRepository;
        this.userRecoveryCodeRepository = userRecoveryCodeRepository;
        this.mailIntegration = mailIntegration;
    }


    @Override
    public UserCredential loadUserByUsernameAndPass(String username, String pass) {

        var userCredentialsOpt = useDetailRepository.findByUsername(username);

        if (userCredentialsOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserCredential userCredentials = userCredentialsOpt.get();

        if (PasswordUtils.matches(pass, userCredentials.getPassword())) {
            return userCredentials;
        }

        throw new BadRequestException("Usuário ou senha inválido");
    }

    @Override
    public void sendRecoveryCode(String email) {

        UserRecoveryCode userRecoveryCode;

        String code = String.format("%04d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {
            var user = useDetailRepository.findByUsername(email);
            if (user.isEmpty()) {
                throw new NotFoundException("Usuário não encontrado");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationDate(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email, "Codígo de recuperação de conta: " + code, "Codigo de recuperação de conta");

    }

    @Override
    public boolean recoveryCodeIsValid(String recoveryCode, String email) {

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }

        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();

        LocalDateTime timeout = userRecoveryCode.getCreationDate().plusMinutes(Long.parseLong(recoveryCodeTimeout));
        LocalDateTime now = LocalDateTime.now();

        return recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timeout);
    }

    @Override
    public void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto) {

        if (recoveryCodeIsValid(userDetailsDto.getRecoveryCode(), userDetailsDto.getEmail())) {
            var userDetails = useDetailRepository.findByUsername(userDetailsDto.getEmail());
            UserCredential userCredential = userDetails.get();

            userCredential.setPassword(PasswordUtils.encode(userDetailsDto.getPassword()));

            useDetailRepository.save(userCredential);
        }

    }




}
