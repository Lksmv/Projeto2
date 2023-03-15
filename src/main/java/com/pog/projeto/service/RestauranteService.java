package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.RestauranteCreateDTO;
import com.pog.projeto.dtos.RestauranteDTO;
import com.pog.projeto.dtos.VooCreateDTO;
import com.pog.projeto.dtos.VooDTO;
import com.pog.projeto.entity.RestauranteEntity;
import com.pog.projeto.entity.VooEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.RestauranteRepository;
import com.pog.projeto.repository.VooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final ObjectMapper objectMapper;
    private final RestauranteRepository repository;

    public RestauranteDTO create(RestauranteCreateDTO dto) {
        return toDTO(toEntity(dto));
    }

    public List<RestauranteDTO> list() {
        return repository.findAll().stream()
                .map(restaurante -> toDTO(restaurante))
                .toList();
    }

    public RestauranteDTO findById(Integer id) throws BusinessException {
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado restaurante")));
    }

    private RestauranteEntity findEntityById(Integer id) throws BusinessException {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado restaurante"));
    }

    public RestauranteDTO atualizar(Integer id, RestauranteCreateDTO dto) throws BusinessException {
        findEntityById(id);
        RestauranteEntity restaurante = toEntity(dto);
        restaurante.setIdRestaurante(id);
        return toDTO(repository.save(restaurante));
    }


    public RestauranteDTO toDTO(RestauranteEntity entity) {
        return objectMapper.convertValue(entity, RestauranteDTO.class);
    }

    public RestauranteEntity toEntity(RestauranteCreateDTO dto) {
        return objectMapper.convertValue(dto, RestauranteEntity.class);
    }

    public RestauranteEntity toEntity(RestauranteDTO dto) {
        return objectMapper.convertValue(dto, RestauranteEntity.class);
    }
}
