package com.esmt.dto;

import com.esmt.enums.UnitType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteFishItem {

    private String fishType;

    private String size;

    private UnitType unitType;   // HEAD / POUNDS

    private Integer quantity;
}
