package com.pog.projeto.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pog.projeto.entity.*;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
public class PacoteDTO {

    private String nome;

    private Integer idPacote;

    private Date dataPartida;

    private Date dataChegada;

    private String promocional;

    private Double valor;

    private Set<HotelDTO> hoteis;

    private Set<RestauranteDTO> restauranteDTOS;

    private Set<VooDTO> vooDTOS;

    private Set<PontoTuristicoDTO> pontoTuristicoDTOS;
}
