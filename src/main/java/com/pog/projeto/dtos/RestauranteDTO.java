package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RestauranteDTO {

    private Integer idRestaurante;

    private String nome;

    private String endereco;

}
