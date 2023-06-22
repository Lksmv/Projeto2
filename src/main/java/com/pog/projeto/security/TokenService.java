package com.pog.projeto.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.TokenDTO;
import com.pog.projeto.entity.PessoaEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String KEY = "CARGOS";
    private final ObjectMapper objectMapper;
    @Value("${jwt.secret}")
    private String secret;

    public TokenDTO getToken(PessoaEntity pessoaEntity) {

        LocalDateTime dataLocalDateTime = LocalDateTime.now();
        Date date = Date.from(dataLocalDateTime.atZone(ZoneId.systemDefault()).plusDays(30).toInstant());

        List<String> cargos = pessoaEntity.getAuthorities().stream()
                .map(pessoa -> pessoa.getAuthority())
                .collect(Collectors.toList());

        String token = Jwts.builder()
                .setIssuer("pog")
                .claim(Claims.ID, pessoaEntity.getIdPessoa().toString())
                .claim(KEY, cargos)
                .setIssuedAt(date)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return new TokenDTO(token, pessoaEntity.getIdPessoa(), pessoaEntity.getCargoEntity().getNome(), pessoaEntity.getNome());
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null)
            return null;
        token = token.replace("Bearer ", "");

        Claims chaves = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        String id = chaves.get(Claims.ID, String.class);
        List<String> cargos = chaves.get(KEY, List.class);
        List<SimpleGrantedAuthority> cargosList = cargos.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return new UsernamePasswordAuthenticationToken(id,
                null, cargosList);
    }
}
