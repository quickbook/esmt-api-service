package com.esmt.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class FishPriceViewDto {
	private Long id;

    private Long fishTypeId;
    private String fishType;

    private Long fishSizeId;
    private String sizeLabel;

    private Long unitTypeId;
    private String unitType;

    private BigDecimal price;
    private String variant; 
    
    private Integer displayOrder;
}
