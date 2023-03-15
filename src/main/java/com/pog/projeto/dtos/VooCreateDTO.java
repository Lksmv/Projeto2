package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class VooCreateDTO {

    private String companhiaAerea;

    private String origem;

    private String destino;

    private Date dataPartida;

    private Date dataChegada;

    private Double valor;

}
