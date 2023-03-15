package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.dtos.HotelCreateDTO;
import com.pog.projeto.dtos.HotelDTO;
import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.exception.BusinessException;
import com.pog.projeto.repository.HotelRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelService {

    private final ObjectMapper objectMapper;
    private final HotelRepositoy hotelRepositoy;

    public HotelDTO create(HotelCreateDTO hotel) {
        return toDTO(toEntity(hotel));
    }

    public List<HotelDTO> list() {
        return hotelRepositoy.findAll().stream()
                .map(hotel -> toDTO(hotel))
                .toList();
    }

    public HotelDTO findById(Integer id) throws BusinessException {
        return toDTO(hotelRepositoy.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado hotel")));
    }

    private HotelEntity findEntityById(Integer id) throws BusinessException {
        return hotelRepositoy.findById(id)
                .orElseThrow(() -> new BusinessException("Não Encontrado hotel"));
    }

    public HotelDTO atualizar(Integer id, HotelCreateDTO hotelDto) throws BusinessException {
        findEntityById(id);
        HotelEntity hotel = toEntity(hotelDto);
        hotel.setIdHotel(id);
        return toDTO(hotelRepositoy.save(hotel));
    }


    public HotelDTO toDTO(HotelEntity hotel) {
        return objectMapper.convertValue(hotel, HotelDTO.class);
    }

    public HotelEntity toEntity(HotelCreateDTO hotel) {
        return objectMapper.convertValue(hotel, HotelEntity.class);
    }

    public HotelEntity toEntity(HotelDTO hotel) {
        return objectMapper.convertValue(hotel, HotelEntity.class);
    }

}


