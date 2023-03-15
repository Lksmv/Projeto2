package com.pog.projeto.repository;

import com.pog.projeto.entity.HotelEntity;
import com.pog.projeto.entity.VooEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VooRepository extends JpaRepository<VooEntity, Integer> {
}
