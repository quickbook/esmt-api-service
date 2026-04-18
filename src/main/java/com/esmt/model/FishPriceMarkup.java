package com.esmt.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "fish_price_markup")
@Data
public class FishPriceMarkup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minAcres;
    private Integer maxAcres;

    @Column(nullable = false)
    private BigDecimal multiplier;

    private Boolean isActive = true;
}