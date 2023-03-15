package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;

@Data
public class PontoTuristicoDTO {

    private Integer idPontoTuristico;

    private String nome;

    private String endereco;

    private String descricao;
}
