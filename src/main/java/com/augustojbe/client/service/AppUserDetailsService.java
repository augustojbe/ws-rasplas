package com.augustojbe.client.service;

import com.augustojbe.client.dto.UserDetailsDto;
import com.augustojbe.client.model.jpa.UserCredential;

public interface AppUserDetailsService {

    UserCredential loadUserByUsernameAndPass(String username, String pass);

    void sendRecoveryCode(String email);

    boolean recoveryCodeIsValid(String recoveryCode, String email);

    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
