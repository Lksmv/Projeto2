package com.pog.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hotel")
public class HotelEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_id_hotel_seq")
    @SequenceGenerator(name = "hotel_id_hotel_seq", sequenceName = "hotel_id_hotel_seq", allocationSize = 1)
    @Id
    @Column(name = "ID_HOTEL")
    private Integer idHotel;
    @Column(name = "NOME")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @Column(name = "DATA_PARTIDA")
    private Date dataPartida;

    @Column(name = "DATA_CHEGADA")
    private Date dataChegada;

    @Column(name = "NUMERO")
    private Integer numero;

    @Column(name = "TELEFONE")
    private Integer telefone;

    @Column(name = "DIARIA")
    private Integer diaria;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_HOTEL", joinColumns = @JoinColumn(name = "ID_HOTEL"), inverseJoinColumns = @JoinColumn(name = "ID_PACOTE"))
    private Set<PacoteEntity> pacoteEntities;
}


