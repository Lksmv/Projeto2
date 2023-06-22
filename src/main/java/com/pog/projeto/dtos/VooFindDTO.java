package com.pog.projeto.dtos;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class VooFindDTO {


    private Instant data;

    private String origem;

    private String destino;

}
