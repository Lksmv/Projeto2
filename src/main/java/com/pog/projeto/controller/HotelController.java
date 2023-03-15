package com.pog.projeto.controller;

import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Validated
@Slf4j
@RestController
@RequestMapping("/hotel")
public class HotelController {

    private final HotelService hotelService;

//    @GetMapping
//    public ResponseEntity<List<HotelEntity>> listar() {
//        List<HotelEntity> lista = hotelService.list();
//        return new ResponseEntity<>(lista, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<HotelEntity> create(@RequestBody HotelEntity hotel) {
//        return new ResponseEntity<>(hotelService.create(hotel), HttpStatus.OK);
//    }

}
