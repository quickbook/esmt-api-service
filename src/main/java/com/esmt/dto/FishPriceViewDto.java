package com.esmt.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FishPriceViewDto {
    private String size;
    private String fishType;
    private BigDecimal price;
    private String unitText;
    private Integer displayOrder;
    private String variant;
}
