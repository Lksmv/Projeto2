package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.HotelCreateDTO;
import com.pog.projeto.dtos.HotelDTO;
import com.pog.projeto.dtos.VooCreateDTO;
import com.pog.projeto.dtos.VooDTO;
import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.VooEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.HotelRepositoy;
import com.pog.projeto.repository.VooRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class VooService {

    private final ObjectMapper objectMapper;
    private final VooRepository vooRepository;

    public VooDTO create(VooCreateDTO voo) {
        return toDTO(toEntity(voo));
    }

    public List<VooDTO> list() {
        return vooRepository.findAll().stream()
                .map(voo -> toDTO(voo))
                .collect(Collectors.toList());
    }

    public VooDTO findById(Integer id) throws BusinessException {
        return toDTO(vooRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado voo")));
    }

    private VooEntity findEntityById(Integer id) throws BusinessException {
        return vooRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado voo"));
    }

    public VooDTO atualizar(Integer id, VooCreateDTO vooDto) throws BusinessException {
        findEntityById(id);
        VooEntity voo = toEntity(vooDto);
        voo.setIdVoo(id);
        return toDTO(vooRepository.save(voo));
    }


    public VooDTO toDTO(VooEntity voo) {
        return objectMapper.convertValue(voo, VooDTO.class);
    }

    public VooEntity toEntity(VooCreateDTO voo) {
        return objectMapper.convertValue(voo, VooEntity.class);
    }

    public VooEntity toEntity(VooDTO voo) {
        return objectMapper.convertValue(voo, VooEntity.class);
    }
}
