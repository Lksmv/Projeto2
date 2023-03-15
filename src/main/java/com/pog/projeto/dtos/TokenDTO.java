package com.pog.projeto.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private Integer idGestor;
    private String cargo;
}