package com.esmt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "package_fish_composition")
@Data
public class PackageFishComposition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quoteType;
    private String packageCode;

    @ManyToOne @JoinColumn(name = "fish_type_id")
    private DmnFishTypes fishType;

    @ManyToOne @JoinColumn(name = "fish_size_id")
    private DmnFishSize fishSize;

    @ManyToOne @JoinColumn(name = "unit_type_id")
    private DmnUnitType unitType;

    private String variant;
    private Integer quantityPerAcre;
    private Integer fixedQuantity;
    private Boolean isActive;
}