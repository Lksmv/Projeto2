package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class HotelCreateDTO {

    @NotNull
    private String nome;
    @NotNull
    private String endereco;
    @NotNull
    private Date dataPartida;
    @NotNull
    private Date dataChegada;
    @NotNull
    private String numero;
    @NotNull
    private String telefone;
    @NotNull
    private Double diaria;
}
