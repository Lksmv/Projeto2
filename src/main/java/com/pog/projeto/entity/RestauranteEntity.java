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
@Entity(name = "RESTAURANTE")
public class RestauranteEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurante_id_restaurante_seq")
    @SequenceGenerator(name = "restaurante_id_restaurante_seq", sequenceName = "restaurante_id_restaurante_seq", allocationSize = 1)
    @Id
    @Column(name = "ID_RESTAURANTE")
    private Integer idRestaurante;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "ENDERECO")
    private String endereco;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PACOTE_RESTAURANTE", joinColumns = @JoinColumn(name = "ID_RESTAURANTE"), inverseJoinColumns = @JoinColumn(name = "ID_PACOTE"))
    private Set<PacoteEntity> pacoteEntities;


}
