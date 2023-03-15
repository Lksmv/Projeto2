package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RestauranteCreateDTO {

    private String nome;

    private String endereco;
}
