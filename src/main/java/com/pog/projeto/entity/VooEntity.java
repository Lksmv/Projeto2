package com.pog.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "VOO")
public class VooEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voo_id_seq")
    @SequenceGenerator(name = "voo_id_seq", sequenceName = "voo_id_seq", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Integer idVoo;

    @Column(name = "COMPANHIA_AEREA ")
    private String companhiaAerea;

    @Column(name = "ORIGEM")
    private String origem;

    @Column(name = "DESTINO")
    private String destino;

    @Column(name = "DATA_PARTIDA")
    private Date dataPartida;

    @Column(name = "DATA_CHEGADA")
    private Date dataChegada;

    @Column(name = "VALOR")
    private Double valor;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_VOO", joinColumns = @JoinColumn(name = "VOO_ID"), inverseJoinColumns = @JoinColumn(name = "PACOTE_ID"))
    private Set<PacoteEntity> pacoteEntities;

}
