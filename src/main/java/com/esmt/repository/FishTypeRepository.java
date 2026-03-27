package com.esmt.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.esmt.model.DmnFishTypes;

@Repository
public interface FishTypeRepository extends JpaRepository<DmnFishTypes, Long> {

    List<DmnFishTypes> findAllByIsActiveTrueOrderByNameAsc();
    Optional<DmnFishTypes> findByName(String name);
    
   
}
