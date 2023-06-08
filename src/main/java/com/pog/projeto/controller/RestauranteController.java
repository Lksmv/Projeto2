package com.pog.projeto.controller;

import com.pog.projeto.dtos.PontoTuristicoCreateDTO;
import com.pog.projeto.dtos.PontoTuristicoDTO;
import com.pog.projeto.dtos.RestauranteCreateDTO;
import com.pog.projeto.dtos.RestauranteDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PontoTuristicoService;
import com.pog.projeto.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurante")
@Validated
@RequiredArgsConstructor
public class RestauranteController {
    private final RestauranteService restauranteService;


    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listar() {
        return new ResponseEntity<>(restauranteService.list(), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<RestauranteDTO> cadastrar(@Valid @RequestBody RestauranteCreateDTO createDto) throws BusinessException {
        RestauranteDTO dto = restauranteService.create(createDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
