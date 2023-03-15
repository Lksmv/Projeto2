package com.pog.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PONTO_TURISTICO")
public class PontoTuristicoEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ponto_turistico_id_seq")
    @SequenceGenerator(name = "ponto_turistico_id_seq", sequenceName = "ponto_turistico_id_seq", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Integer idPontoTuristico;

    @Column(name = "NOME ")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_PONTO_TURISTCO", joinColumns = @JoinColumn(name = "PONTO_TURISTICO_ID"), inverseJoinColumns = @JoinColumn(name = "PACOTE_ID"))
    private Set<PacoteEntity> pacoteEntities;
}
