package com.esmt.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "quote_package_master")
@Data
public class QuotePackageMaster {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quoteType;
    private String packageCode;
    private String packageName;
    private String description;

    private Boolean recommended;
    private BigDecimal minimumOrderAmount;
    private Boolean isActive;
}