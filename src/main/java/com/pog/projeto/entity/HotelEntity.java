package com.pog.projeto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hotel")
public class HotelEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_id_seq")
    @SequenceGenerator(name = "hotel_id_seq", sequenceName = "hotel_id_seq", allocationSize = 1)
    @Id
    @Column(name = "ID")
    private Integer idHotel;
    @Column(name = "NOME")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_HOTEL", joinColumns = @JoinColumn(name = "HOTEL_ID"), inverseJoinColumns = @JoinColumn(name = "PACOTE_ID"))
    private Set<PacoteEntity> hoteis;
}


