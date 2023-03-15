package com.pog.projeto.repository;

import com.pog.projeto.entity.PessoaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntity, Integer> {

    public Optional<PessoaEntity> findPessoaEntityByEmail(String email);
}
