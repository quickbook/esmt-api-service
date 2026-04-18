package com.esmt.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FreightDto {

    private Long id;

    private String vehicleType; // PICKUP / BIG_TRUCK

    private BigDecimal ratePerMile;

    private Boolean active;
}