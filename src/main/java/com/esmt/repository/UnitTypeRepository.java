package com.esmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.DmnUnitType;

public interface UnitTypeRepository extends JpaRepository<DmnUnitType, Long> {

}
