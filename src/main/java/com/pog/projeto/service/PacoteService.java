package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.CargoDTO;
import com.pog.projeto.dtos.PacoteDTO;
import com.pog.projeto.dtos.PessoaCreateDTO;
import com.pog.projeto.dtos.PessoaDTO;
import com.pog.projeto.entity.PacoteEntity;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.PacoteRepository;
import com.pog.projeto.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PacoteService {

    private final ObjectMapper objectMapper;
    private final PacoteRepository repository;
    private final HotelService hotelService;
    private final PontoTuristicoService pontoTuristicoService;
    private final VooService vooService;
    private final PessoaRepository pessoaRepository;
    private final RestauranteService restauranteService;

    public PacoteDTO create(Integer idPessoa) {
        PacoteEntity pacoteEntity = new PacoteEntity();
        pacoteEntity.setPessoas(Collections.emptySet());
        pacoteEntity.setHoteis(Collections.emptySet());
        pacoteEntity.setRestauranteEntities(Collections.emptySet());
        pacoteEntity.setVooEntities(Collections.emptySet());
        pacoteEntity.setPontoTuristicoEntities(Collections.emptySet());
        pacoteEntity.getPessoas().add(pessoaRepository.findById(idPessoa).get());
        return toDTO(repository.save(pacoteEntity));
    }

    public PacoteDTO toDTO(PacoteEntity pacoteEntity) {
        PacoteDTO pacoteDTO = objectMapper.convertValue(pacoteEntity, PacoteDTO.class);
        pacoteDTO.setRestauranteDTOS(pacoteEntity.getRestauranteEntities().stream()
                .map(restaurante -> restauranteService.toDTO(restaurante))
                .collect(Collectors.toSet()));
        pacoteDTO.setVooDTOS(pacoteEntity.getVooEntities().stream()
                .map(vooEntity -> vooService.toDTO(vooEntity))
                .collect(Collectors.toSet()));
        pacoteDTO.setHoteis(pacoteEntity.getHoteis().stream()
                .map(hotel -> hotelService.toDTO(hotel))
                .collect(Collectors.toSet()));
        pacoteDTO.setPontoTuristicoDTOS(pacoteEntity.getPontoTuristicoEntities().stream()
                .map(pontoTuristicoEntity -> pontoTuristicoService.toDTO(pontoTuristicoEntity))
                .collect(Collectors.toSet()));
        return pacoteDTO;
    }

}
