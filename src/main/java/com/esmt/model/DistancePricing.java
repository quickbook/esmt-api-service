package com.esmt.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "distance_pricing")
@Data
public class DistancePricing {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minMiles;
    private Integer maxMiles;
    private BigDecimal costPerMile;

    private Boolean isActive;
}