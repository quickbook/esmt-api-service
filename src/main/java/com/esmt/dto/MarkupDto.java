package com.esmt.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MarkupDto {

    private Long id;

    private Integer minAcres;
    private Integer maxAcres;

    private BigDecimal multiplier;

    private Boolean active;
}