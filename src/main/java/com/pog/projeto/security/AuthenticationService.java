package com.pog.projeto.security;

import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final PessoaService pessoaService;

    @Override
    public UserDetails loadUserByUsername(String loginUsername) throws UsernameNotFoundException {
        try {
            PessoaEntity user = pessoaService.findByEmail(loginUsername);
            return user;
        } catch (BusinessException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}