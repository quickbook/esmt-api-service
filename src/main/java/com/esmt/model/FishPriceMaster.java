package com.esmt.model;

import java.math.BigDecimal;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne; 
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "fish_price_master")
public class FishPriceMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fish_type_id", nullable = false)
    private DmnFishTypes fishType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fish_size_id", nullable = false)
    private DmnFishSize fishSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_type_id", nullable = false)
    private DmnUnitType unitType;

    @Column(name = "variant", length = 50)
    private String variant;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "is_active")
    private Boolean isActive = true;     
 
}
