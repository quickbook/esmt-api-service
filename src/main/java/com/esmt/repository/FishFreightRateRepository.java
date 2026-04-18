package com.esmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.FishFreightRate;

public interface FishFreightRateRepository extends JpaRepository<FishFreightRate, Long> {

    List<FishFreightRate> findByIsActiveTrue();

    Optional<FishFreightRate> findByVehicleTypeAndIsActiveTrue(String vehicleType);
}