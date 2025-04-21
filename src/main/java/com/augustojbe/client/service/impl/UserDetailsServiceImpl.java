package com.augustojbe.client.service.impl;

import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.repository.UseDetailRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UseDetailRepository useDetailRepository;

    public UserDetailsServiceImpl(UseDetailRepository useDetailRepository) {
        this.useDetailRepository = useDetailRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return useDetailRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    }
}
