package com.pog.projeto.controller;

import com.pog.projeto.dtos.PontoTuristicoCreateDTO;
import com.pog.projeto.dtos.PontoTuristicoDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PontoTuristicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ponto-turistico")
@Validated
@RequiredArgsConstructor
public class PontoTuristicoController {

    private final PontoTuristicoService pontoTuristicoService;


    @GetMapping
    public ResponseEntity<List<PontoTuristicoDTO>> listar() {
        return new ResponseEntity<>(pontoTuristicoService.list(), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PontoTuristicoDTO> cadastrar(@Valid @RequestBody PontoTuristicoCreateDTO pontoTuristicoCreateDTO) throws BusinessException {
        PontoTuristicoDTO dto = pontoTuristicoService.create(pontoTuristicoCreateDTO);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping
    public void delete(@RequestParam Integer id) throws BusinessException {
        pontoTuristicoService.delete(id);
        ResponseEntity.ok();
    }
}
