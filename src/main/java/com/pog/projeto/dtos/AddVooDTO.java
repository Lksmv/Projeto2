package com.pog.projeto.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AddVooDTO {
    private List<Integer> idVoos;
    private Integer idPacote;
}
