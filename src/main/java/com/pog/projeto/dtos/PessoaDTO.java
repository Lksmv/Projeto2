package com.pog.projeto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pog.projeto.entity.CargoEntity;
import com.pog.projeto.entity.PacoteEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
public class PessoaDTO {

    private Integer idPessoa;

    private String nome;

    private String cpf;

    private String email;

    private String telefone;

    private Set<PacoteDTO> pacoteDTOSet;

    private CargoDTO cargo;
}
