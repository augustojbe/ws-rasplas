package com.augustojbe.client.filter;

import com.augustojbe.client.exception.NotFoundException;
import com.augustojbe.client.model.jpa.UserCredential;
import com.augustojbe.client.repository.jpa.UseDetailRepository;
import com.augustojbe.client.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UseDetailRepository useDetailRepository;

    public AuthenticationFilter(TokenService tokenService, UseDetailRepository useDetailRepository) {
        this.tokenService = tokenService;
        this.useDetailRepository = useDetailRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getBearerToken(request);

        if(tokenService.isValid(token)){
            authByToken(token);

        }
        filterChain.doFilter(request, response);

    }

    private void authByToken(String token) {

        // recupera id do usuario
        Long userId = tokenService.getUserId(token);
        var userOpt = useDetailRepository.findById(userId);

        if (userOpt.isEmpty()){
            throw new NotFoundException("Usuário não encontrado");
        }

        UserCredential userCredential = userOpt.get();

        //autenticação no spring

        UsernamePasswordAuthenticationToken userAuth
                = new UsernamePasswordAuthenticationToken(userCredential, null, null);
        SecurityContextHolder.getContext().setAuthentication(userAuth);

    }

    private String getBearerToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(Objects.isNull(token) || !token.startsWith("Bearer")){
            return null;
        }
        return token.substring(7, token.length());

    }
}
