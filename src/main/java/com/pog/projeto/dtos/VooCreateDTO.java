package com.pog.projeto.dtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class VooCreateDTO {

    private String companhiaAerea;

    private String origem;

    private String destino;

    private LocalDateTime dataPartida;

    private Double valor;

}
