package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Data
public class HotelCreateDTO {

    @NotNull
    private String nome;
    @NotNull
    private String endereco;
}
