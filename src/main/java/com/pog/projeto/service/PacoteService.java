package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.*;
import com.pog.projeto.entity.PacoteEntity;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.PacoteRepository;
import com.pog.projeto.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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

    public PacoteDTO create() {
        PacoteEntity pacoteEntity = new PacoteEntity();
        pacoteEntity.setPessoas(Collections.emptySet());
        pacoteEntity.setHoteis(Collections.emptySet());
        pacoteEntity.setRestauranteEntities(Collections.emptySet());
        pacoteEntity.setVooEntities(Collections.emptySet());
        pacoteEntity.setPontoTuristicoEntities(Collections.emptySet());
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())).get();
        if (pessoaEntity.getCargoEntity().getNome().equals("ROLE_ADMIN")) {
            pacoteEntity.setPromocional("S");
        } else {
            pacoteEntity.setPromocional("N");
        }
        pacoteEntity.getPessoas().add(pessoaEntity);
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

    public List<PacoteListagemDTO> pacotesUsuarioLogado() {
        PessoaEntity pessoaEntity = pessoaRepository.findById(Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())).get();
        if (pessoaEntity.getCargoEntity().getNome().equals("ROLE_ADMIN")) {
            return pessoaEntity.getPacoteEntities().stream()
                    .map(pacoteEntity -> objectMapper.convertValue(pacoteEntity, PacoteListagemDTO.class))
                    .collect(Collectors.toList());
        } else {
            return pessoaEntity.getPacoteEntities().stream()
                    .filter(pacoteEntity -> pacoteEntity.getPromocional() == "N")
                    .map(pacoteEntity -> objectMapper.convertValue(pacoteEntity, PacoteListagemDTO.class))
                    .collect(Collectors.toList());
        }
    }

}
