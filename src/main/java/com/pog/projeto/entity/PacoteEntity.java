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
@Entity(name = "PACOTE")
public class PacoteEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pacote_id_seq")
    @SequenceGenerator(name = "pacote_id_seq", sequenceName = "pacote_id_seq", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Integer idPacote;

    @Column(name = "DATA_PARTIDA")
    private Date dataPartida;

    @Column(name = "DATA_CHEGADA")
    private Date dataChegada;

    @Column(name = "PROMOCIONAL")
    private String promocional;

    @Column(name = "VALOR")
    private Double valor;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_HOTEL",
            joinColumns = @JoinColumn(name = "PACOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "HOTEL_ID")
    )
    private Set<HotelEntity> hoteis;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_HOTEL",
            joinColumns = @JoinColumn(name = "PACOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PESSOA_ID")
    )
    private Set<PessoaEntity> pessoas;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_RESTAURANTE",
            joinColumns = @JoinColumn(name = "PACOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "RESTAURANTE_ID")
    )
    private Set<RestauranteEntity> restauranteEntities;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_VOO",
            joinColumns = @JoinColumn(name = "PACOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "VOO_ID")
    )
    private Set<VooEntity> vooEntities;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_PONTO_TURISTICO",
            joinColumns = @JoinColumn(name = "PACOTE_ID"),
            inverseJoinColumns = @JoinColumn(name = "PONTO_TURISTICO_ID")
    )
    private Set<PontoTuristicoEntity> pontoTuristicoEntities;
}
