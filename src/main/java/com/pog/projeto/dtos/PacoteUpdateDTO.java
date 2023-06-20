package com.pog.projeto.dtos;

import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class PacoteUpdateDTO {

        private String nome;

        private Date dataPartida;

        private Date dataChegada;

        private Double valor;

        private String cidade;

}
