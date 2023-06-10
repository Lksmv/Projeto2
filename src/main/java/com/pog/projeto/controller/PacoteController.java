package com.pog.projeto.controller;

import com.pog.projeto.dtos.PacoteDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PacoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pacote")
@Validated
@RequiredArgsConstructor
public class PacoteController {

    private final PacoteService pacoteService;

//    @GetMapping
//    public ResponseEntity<List<PacoteDTO>> listar() {
//        return new ResponseEntity<>(pacoteService.(), HttpStatus.OK);
//    }

    @PostMapping("/create")
    public ResponseEntity<PacoteDTO> criarPacote() throws BusinessException {
        PacoteDTO dto = pacoteService.create();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
