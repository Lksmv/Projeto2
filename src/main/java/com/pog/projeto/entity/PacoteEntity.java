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

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pacote_id_pacote_seq")
    @SequenceGenerator(name = "pacote_id_pacote_seq", sequenceName = "pacote_id_pacote_seq", allocationSize = 1)
    @Id
    @Column(name = "ID_PACOTE")
    private Integer idPacote;

    @Column(name = "DATA_PARTIDA")
    private Date dataPartida;

    @Column(name = "DATA_CHEGADA")
    private Date dataChegada;

    @Column(name = "PROMOCIONAL")
    private String promocional;

    @Column(name = "CIDADE")
    private String cidade;

    @Column(name = "VALOR")
    private Double valor;

    @Column(name = "NOME_PACOTE")
    private String nome;

    @Column(name = "qnt_pessoa")
    private Integer qntPessoa;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_HOTEL",
            joinColumns = @JoinColumn(name = "ID_PACOTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_HOTEL")
    )
    private Set<HotelEntity> hoteis;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_PESSOA",
            joinColumns = @JoinColumn(name = "ID_PACOTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_PESSOA")
    )
    private Set<PessoaEntity> pessoas;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_VOO",
            joinColumns = @JoinColumn(name = "ID_PACOTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_VOO")
    )
    private Set<VooEntity> vooEntities;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "PACOTE_PONTO_TURISTICO",
            joinColumns = @JoinColumn(name = "ID_PACOTE"),
            inverseJoinColumns = @JoinColumn(name = "ID_PONTO")
    )
    private Set<PontoTuristicoEntity> pontoTuristicoEntities;
}
