package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class HotelDTO {

    private Integer idHotel;

    private String nome;

    private String endereco;

    private Date dataPartida;

    private Date dataChegada;

    private String numero;

    private String telefone;

    private Double diaria;
}
