package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.PontoTuristicoCreateDTO;
import com.pog.projeto.dtos.PontoTuristicoDTO;
import com.pog.projeto.entity.PontoTuristicoEntity;
import com.pog.projeto.entity.VooEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.PontoTuristicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PontoTuristicoService {

    private final ObjectMapper objectMapper;
    private final PontoTuristicoRepository repository;

    public PontoTuristicoDTO create(PontoTuristicoCreateDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    public List<PontoTuristicoDTO> list() {
        return repository.findAll().stream()
                .map(restaurante -> toDTO(restaurante))
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws BusinessException {
        PontoTuristicoEntity entity = findEntityById(id);
        repository.delete(entity);
    }
    
    public PontoTuristicoEntity findEntityById(Integer id) throws BusinessException {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado ponto turistico"));
    }

    public PontoTuristicoDTO toDTO(PontoTuristicoEntity entity) {
        return objectMapper.convertValue(entity, PontoTuristicoDTO.class);
    }

    public PontoTuristicoEntity toEntity(PontoTuristicoCreateDTO dto) {
        return objectMapper.convertValue(dto, PontoTuristicoEntity.class);
    }

}
