package com.esmt.dto;

import java.math.BigDecimal;

import com.esmt.enums.UnitType;

import lombok.Data;

@Data
public class FishPriceDto {

    private Long id;

    private String fishType;

    private String sizeLabel;

    private UnitType unitType;

    private BigDecimal price;

    private String variant;

    private Boolean active;
}