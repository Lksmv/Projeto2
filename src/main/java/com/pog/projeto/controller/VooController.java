package com.pog.projeto.controller;

import com.pog.projeto.dtos.RestauranteCreateDTO;
import com.pog.projeto.dtos.RestauranteDTO;
import com.pog.projeto.dtos.VooCreateDTO;
import com.pog.projeto.dtos.VooDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.RestauranteService;
import com.pog.projeto.service.VooService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/voo")
@Validated
@RequiredArgsConstructor
public class VooController {

    private final VooService vooService;


    @GetMapping
    public ResponseEntity<List<VooDTO>> listar() {
        return new ResponseEntity<>(vooService.list(), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<VooDTO> cadastrar(@Valid @RequestBody VooCreateDTO createDto) throws BusinessException {
        VooDTO dto = vooService.create(createDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
