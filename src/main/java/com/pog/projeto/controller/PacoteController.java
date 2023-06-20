package com.pog.projeto.controller;

import com.pog.projeto.dtos.PacoteDTO;
import com.pog.projeto.dtos.PacoteUpdateDTO;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.service.PacoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @PostMapping("/atualizar")
    public ResponseEntity<PacoteDTO> atualizarPacote(@RequestParam Integer idPacote,
                                                     @RequestParam(required = false) String nome,
                                                     @RequestParam(required = false) Date dataPartida,
                                                     @RequestParam(required = false) Date dataChegada,
                                                     @RequestParam(required = false) String cidade) throws BusinessException {
        PacoteDTO dto = pacoteService.atualizar(idPacote, nome,dataPartida,dataChegada,cidade);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    private String nome;

    private Date dataPartida;

    private Date dataChegada;

    private Double valor;

    private String cidade;

    @GetMapping("/get-by-id")
    public ResponseEntity<PacoteDTO> getById(@RequestParam Integer idPacote) throws BusinessException {
        PacoteDTO dto = pacoteService.getById(idPacote);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add-hotel")
    public ResponseEntity<PacoteDTO> adicionarHotel(@RequestParam Integer idHotel, Integer idPacote) throws BusinessException {
        PacoteDTO dto = pacoteService.adicionarHotel(idHotel, idPacote);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add-ponto")
    public ResponseEntity<PacoteDTO> adicionarPontoTuristico(@RequestParam Integer idPonto, Integer idPacote) throws BusinessException {
        PacoteDTO dto = pacoteService.adicionarPontoTuristico(idPonto, idPacote);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add-voo")
    public ResponseEntity<PacoteDTO> adicionarVoo(@RequestParam Integer idVoo, Integer idPacote) throws BusinessException {
        PacoteDTO dto = pacoteService.adicionarVoo(idVoo, idPacote);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add-restaurante")
    public ResponseEntity<PacoteDTO> adicionarRestaurante(@RequestParam Integer idrestaurante, Integer idPacote) throws BusinessException {
        PacoteDTO dto = pacoteService.adicionarRestaurante(idrestaurante, idPacote);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
