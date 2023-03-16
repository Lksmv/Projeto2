package com.pog.projeto.repository;

import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.PacoteEntity;
import com.pog.projeto.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacoteRepository extends JpaRepository<PacoteEntity, Integer> {

}
