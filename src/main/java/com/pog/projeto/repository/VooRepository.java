package com.pog.projeto.repository;

import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.PessoaEntity;
import com.pog.projeto.entity.VooEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VooRepository extends JpaRepository<VooEntity, Integer> {

    public List<VooEntity> findVooEntitiesByDataPartidaBetweenAndOrigemAndDestino(LocalDateTime dataInicio,LocalDateTime dataFim, String origem,String destino);
}
