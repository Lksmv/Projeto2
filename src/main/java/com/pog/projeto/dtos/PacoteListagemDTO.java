package com.pog.projeto.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class PacoteListagemDTO {

    private Integer idPacote;

    private String nome;

    private String cidade;

    private String promocional;
}
