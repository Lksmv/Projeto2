package com.pog.projeto.controller;

import com.pog.projeto.dtos.VooCreateDTO;
import com.pog.projeto.dtos.VooDTO;
import com.pog.projeto.dtos.VooFindDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.VooService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/voo")
@Validated
@RequiredArgsConstructor
public class VooController {

    private final VooService vooService;


    @PostMapping("/cadastro")
    public ResponseEntity<VooDTO> cadastrar(@Valid @RequestBody VooCreateDTO createDto) throws BusinessException {
        VooDTO dto = vooService.create(createDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/get-voo")
    public ResponseEntity<List<VooDTO>> listarVoos(@RequestBody VooFindDTO vooFindDTO) {
        return new ResponseEntity<>(vooService.findVoo(vooFindDTO), HttpStatus.OK);
    }


    @DeleteMapping
    public void delete(@RequestParam Integer id) throws BusinessException {
        vooService.delete(id);
        ResponseEntity.ok();
    }
}
