package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.*;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.entity.PontoTuristicoEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.PacoteRepository;
import com.pog.projeto.repository.PessoaRepository;
import com.pog.projeto.repository.PontoTuristicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PessoaService {

    private final ObjectMapper objectMapper;
    private final PacoteService pacoteService;
    private final CargoService cargoService;
    private final PessoaRepository pessoaRepository;

    private final PasswordEncoder passwordEncoder;

    public PessoaDTO cadastrar(PessoaCreateDTO pessoaCreateDTO) throws BusinessException {
        if (pessoaRepository.findAllByEmailOrCpf(pessoaCreateDTO.getEmail(), pessoaCreateDTO.getCpf()).isPresent()) {
            throw new BusinessException("Email ou CPF Já cadastrados");
        }
        PessoaEntity pessoaEntity = toEntity(pessoaCreateDTO);
        pessoaEntity.setCargoEntity(cargoService.findById(2));
        pessoaEntity.setSenha(passwordEncoder.encode(pessoaCreateDTO.getSenha()));
        return toDTO(pessoaRepository.save(pessoaEntity));
    }


    public void updateNome(String nome) {
        String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
        pessoaEntity.setNome(nome);
        pessoaRepository.save(pessoaEntity);
    }

    public void updateCpf(String cpf) throws BusinessException {
        String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (pessoaRepository.findPessoaEntityByCpf(cpf).isPresent()) {
            throw new BusinessException("Email ou CPF Já cadastrados");
        }
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
        pessoaEntity.setCpf(cpf);
        pessoaRepository.save(pessoaEntity);
    }

    public void updateEmail(String email) throws BusinessException {
        String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        if (pessoaRepository.findPessoaEntityByEmail(email).isPresent()) {
            throw new BusinessException("Email ou CPF Já cadastrados");
        }
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
        pessoaEntity.setEmail(email);
        pessoaRepository.save(pessoaEntity);
    }

    public void updateTelefone(String telefone) throws BusinessException {
        String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
        pessoaEntity.setTelefone(telefone);
        pessoaRepository.save(pessoaEntity);
    }

    public void updateSenha(String senha) throws BusinessException {
        String idPessoa = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(idPessoa)).get();
        pessoaEntity.setSenha(passwordEncoder.encode(senha));
        pessoaRepository.save(pessoaEntity);
    }

    public List<PessoaDTO> list() {
        return pessoaRepository.findAll().stream()
                .map(pessoaEntity -> toDTO(pessoaEntity))
                .collect(Collectors.toList());
    }

    public PessoaEntity findById(Integer idPessoa) throws BusinessException {
        return pessoaRepository.findById(idPessoa)
                .orElseThrow(() -> new BusinessException("Pessoa não encontrada"));
    }

    public PessoaEntity findByEmail(String email) throws BusinessException {
        return pessoaRepository.findPessoaEntityByEmail(email)
                .orElseThrow(() -> new BusinessException("Pessoa não encontrada"));
    }


    public PessoaDTO toDTO(PessoaEntity entity) {
        PessoaDTO pessoaDTO = objectMapper.convertValue(entity, PessoaDTO.class);
        pessoaDTO.setCargo(objectMapper.convertValue(entity.getCargoEntity(), CargoDTO.class));
        return pessoaDTO;
    }

    public PessoaEntity toEntity(PessoaCreateDTO dto) throws BusinessException {
        PessoaEntity pessoaEntity = objectMapper.convertValue(dto, PessoaEntity.class);
        pessoaEntity.setPacoteEntities(Collections.emptySet());
        return pessoaEntity;
    }

}
