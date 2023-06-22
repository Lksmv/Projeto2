package com.pog.projeto.controller;

import com.pog.projeto.dtos.PacoteDTO;
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

    @GetMapping("/get-logged-user")
    public ResponseEntity<PessoaDTO> getUsuarioLogado() {
        return new ResponseEntity<>(pessoaService.getUsuarioLogado(), HttpStatus.OK);
    }

    @PutMapping("/update-nome")
    public void updateNome(@RequestParam String nome) {
        pessoaService.updateNome(nome);
        ResponseEntity.ok();
    }

    @PutMapping("/update-telefone")
    public void updateTelefone(@RequestParam String telefone) throws BusinessException {
        pessoaService.updateTelefone(telefone);
        ResponseEntity.ok();
    }

    @PutMapping("/update-senha")
    public void updateSenha(@RequestParam String senha) throws BusinessException {
        pessoaService.updateSenha(senha);
        ResponseEntity.ok();
    }

    @PutMapping("/add-pacote")
    public void updateSenha(@RequestParam Integer idPacote) throws BusinessException {
        pessoaService.addPacote(idPacote);
        ResponseEntity.ok();
    }

    @PutMapping("/update-email")
    public void updateEmail(@RequestParam String email) throws BusinessException {
        pessoaService.updateEmail(email);
        ResponseEntity.ok();
    }

    @PutMapping("/update-cpf")
    public void updateCpf(@RequestParam String cpf) throws BusinessException {
        pessoaService.updateCpf(cpf);
        ResponseEntity.ok();
    }

    @PostMapping("/cadastro")
    public ResponseEntity<PessoaDTO> cadastroPessoa(@Valid @RequestBody PessoaCreateDTO pessoaCreateDTO) throws BusinessException {
        PessoaDTO pessoaDTO = pessoaService.cadastrar(pessoaCreateDTO);
        return ResponseEntity.ok(pessoaDTO);
    }

    @GetMapping("/pacotes")
    public ResponseEntity<List<PacoteDTO>> pacotesUsuarioLogado() {
        return new ResponseEntity<>(pacoteService.pacotesUsuarioLogado(), HttpStatus.OK);
    }


}
