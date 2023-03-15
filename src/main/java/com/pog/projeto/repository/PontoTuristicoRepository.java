package com.pog.projeto.repository;

import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.PontoTuristicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoTuristicoRepository extends JpaRepository<PontoTuristicoEntity, Integer> {
}
