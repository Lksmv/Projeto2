package com.pog.projeto.repository;

import com.pog.projeto.entity.CargoEntity;
import com.pog.projeto.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
}
