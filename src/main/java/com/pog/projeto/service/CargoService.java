package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.CargoDTO;
import com.pog.projeto.entity.CargoEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;
    private final ObjectMapper objectMapper;

    public CargoEntity findById(Integer id) throws BusinessException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Cargo não encontrado"));
    }

    public CargoDTO cargoToDTO(CargoEntity cargo) {
        return objectMapper.convertValue(cargo, CargoDTO.class);
    }

    public CargoEntity cargoToEntity(CargoDTO cargo) {
        return objectMapper.convertValue(cargo, CargoEntity.class);
    }
}