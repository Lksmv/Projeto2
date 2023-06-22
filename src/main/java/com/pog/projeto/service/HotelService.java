package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.HotelCreateDTO;
import com.pog.projeto.dtos.HotelDTO;
import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.PacoteEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.HotelRepositoy;
import com.pog.projeto.repository.PacoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HotelService {

    private final ObjectMapper objectMapper;
    private final HotelRepositoy hotelRepositoy;

    public HotelDTO create(HotelCreateDTO hotel) throws BusinessException, ParseException {
        HotelEntity hotel1 = toEntity(hotel);
        Set<PacoteEntity> pacoteEntitySet = new HashSet<PacoteEntity>();
        hotel1.setPacoteEntities(pacoteEntitySet);
        hotel1 = hotelRepositoy.save(hotel1);
        return toDTO(hotel1);
    }

    public List<HotelDTO> list() {
        return hotelRepositoy.findAll().stream()
                .map(hotel -> toDTO(hotel))
                .collect(Collectors.toList());
    }

    public HotelDTO findById(Integer id) throws BusinessException {
        return toDTO(hotelRepositoy.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado hotel")));
    }

    public HotelEntity findEntityById(Integer id) throws BusinessException {
        return hotelRepositoy.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado hotel"));
    }

    public HotelDTO atualizar(Integer id, HotelCreateDTO hotelDto) throws BusinessException {
        findEntityById(id);
        HotelEntity hotel = toEntity(hotelDto);
        hotel.setIdHotel(id);
        return toDTO(hotelRepositoy.save(hotel));
    }

    public void delete(Integer id) throws BusinessException {
        findEntityById(id);
        hotelRepositoy.deleteById(id);
    }


    public HotelDTO toDTO(HotelEntity hotel) {
        return objectMapper.convertValue(hotel, HotelDTO.class);
    }

    public HotelEntity toEntity(HotelCreateDTO hotel) {
        return objectMapper.convertValue(hotel, HotelEntity.class);
    }


}


