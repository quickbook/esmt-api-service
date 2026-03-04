package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.DmnPondAccess;

public interface PondAccessRepository extends JpaRepository<DmnPondAccess, Long>{

    List<DmnPondAccess> findAllByIsActiveTrueOrderByNameAsc();

    
}
