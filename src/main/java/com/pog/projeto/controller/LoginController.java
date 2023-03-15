package com.pog.projeto.controller;

import antlr.Token;
import com.pog.projeto.dtos.LoginDTO;
import com.pog.projeto.dtos.TokenDTO;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.security.TokenService;
import com.pog.projeto.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final PessoaService pessoaService;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> auth(@RequestBody @Valid LoginDTO loginDTO) throws BusinessException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                );
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // UsuarioEntity
        Object principal = authenticate.getPrincipal();
        PessoaEntity pessoaEntity = (PessoaEntity) principal;
        TokenDTO token = tokenService.getToken(pessoaEntity);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }
}
