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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ponto_turistico_id_ponto_seq")
    @SequenceGenerator(name = "ponto_turistico_id_ponto_seq", sequenceName = "ponto_turistico_id_ponto_seq", allocationSize = 1)
    @Id
    @Column(name = "ID_PONTO")
    private Integer idPontoTuristico;

    @Column(name = "NOME ")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "DESCRICAO")
    private String descricao;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_PONTO_TURISTCO", joinColumns = @JoinColumn(name = "ID_PONTO_TURISTICO"), inverseJoinColumns = @JoinColumn(name = "ID_PACOTE"))
    private Set<PacoteEntity> pacoteEntities;
}
