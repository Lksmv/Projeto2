package com.pog.projeto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pog.projeto.entity.CargoEntity;
import com.pog.projeto.entity.PacoteEntity;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class PessoaCreateDTO {

    @NotNull
    private String nome;
    @NotNull
    @CPF
    private String cpf;
    @Email
    private String email;
    @NotNull
    @Max(15)
    private String telefone;
    @NotNull
    private String senha;

    private Integer idCargo;
}
