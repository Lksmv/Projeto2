package com.pog.projeto.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull
    @Schema(description = "Email")
    private String email;

    @NotNull
    @Schema(description = "Senha")
    private String senha;
}
