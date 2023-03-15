package com.pog.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "CARGO")
public class CargoEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cargo_id_seq")
    @SequenceGenerator(name = "cargo_id_seq", sequenceName = "cargo_id_seq", allocationSize = 1)
    @Column(name = "ID_CARGO")
    private Integer idCargo;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cargoEntity", fetch = FetchType.LAZY)
    private Set<PessoaEntity> pessoa;

    @Override
    public String getAuthority() {
        return nome;
    }
}