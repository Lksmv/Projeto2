package com.pog.projeto.controller;

import com.pog.projeto.dtos.PacoteListagemDTO;
import com.pog.projeto.dtos.PessoaCreateDTO;
import com.pog.projeto.dtos.PessoaDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PacoteService;
import com.pog.projeto.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pessoa")
@Validated
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService pessoaService;
    private final PacoteService pacoteService;


    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listar() {
        return new ResponseEntity<>(pessoaService.list(), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PessoaDTO> cadastroPessoa(@Valid @RequestBody PessoaCreateDTO pessoaCreateDTO) throws BusinessException {
        PessoaDTO pessoaDTO = pessoaService.cadastrar(pessoaCreateDTO);
        return ResponseEntity.ok(pessoaDTO);
    }

    @GetMapping("/pacotes")
    public ResponseEntity<List<PacoteListagemDTO>> pacotesUsuarioLogado() {
        return new ResponseEntity<>(pacoteService.pacotesUsuarioLogado(), HttpStatus.OK);
    }



}
