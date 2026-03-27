package com.esmt.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esmt.model.DistancePricing;

public interface DistancePricingRepository extends JpaRepository<DistancePricing, Long> {

    @Query("""
        SELECT d.costPerMile FROM DistancePricing d
        WHERE :miles BETWEEN d.minMiles AND d.maxMiles
        AND d.isActive = true
    """)
    BigDecimal findRate(Integer miles);
}