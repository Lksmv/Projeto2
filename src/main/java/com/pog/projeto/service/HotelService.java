package com.pog.projeto.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.repository.HotelRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HotelService {

    private final ObjectMapper objectMapper;
    private final HotelRepositoy hotelRepositoy;

    public HotelEntity create(HotelEntity hotel) {
        return hotelRepositoy.save(hotel);
    }

    public List<HotelEntity> list() {
        return hotelRepositoy.findAll();
    }

}


