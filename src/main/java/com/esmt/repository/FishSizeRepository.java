package com.esmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.DmnFishSize;

public interface FishSizeRepository extends JpaRepository<DmnFishSize, Long> {

    List<DmnFishSize> findAllByIsActiveTrueOrderByNameAsc();
    Optional<DmnFishSize> findByName(String name);
    
}
