package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;

@Data
public class HotelDTO {

    private Integer idHotel;

    private String nome;

    private String endereco;
}
