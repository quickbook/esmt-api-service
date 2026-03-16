package com.esmt.dto;

import java.math.BigDecimal;

import com.esmt.enums.UnitType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteQuantityInput {

    private String fishType;

    private UnitType unitType;

    private Integer recommendedMin;
    
    private Integer quantity;
    
    private BigDecimal unitPrice; 

    private Integer recommendedMax;

    private String recommendationText;
}