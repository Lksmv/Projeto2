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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public PessoaEntity findByEmail(String email) throws BusinessException {
        return pessoaRepository.findPessoaEntityByEmail(email)
                .orElseThrow(() -> new BusinessException("Pessoa não encontrada"));
    }


    public PessoaDTO toDTO(PessoaEntity entity) {
        PessoaDTO pessoaDTO = objectMapper.convertValue(entity, PessoaDTO.class);
        pessoaDTO.setCargo(objectMapper.convertValue(entity.getCargoEntity(), CargoDTO.class));
        pessoaDTO.setPacoteDTOSet(entity.getPacoteEntities().stream()
                .map(pacoteEntity -> pacoteService.toDTO(pacoteEntity))
                .collect(Collectors.toSet()));
        return pessoaDTO;
    }

    public PessoaEntity toEntity(PessoaCreateDTO dto) throws BusinessException {
        PessoaEntity pessoaEntity = objectMapper.convertValue(dto, PessoaEntity.class);
        pessoaEntity.setPacoteEntities(Collections.emptySet());
        pessoaEntity.setCargoEntity(cargoService.findById(dto.getIdCargo()));
        return pessoaEntity;
    }

}
