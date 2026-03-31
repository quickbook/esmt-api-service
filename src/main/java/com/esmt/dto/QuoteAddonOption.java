package com.esmt.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteAddonOption {

    private String addonCode;

    private String fishSize;

    private String fishName;

    private Integer quantity;

    private BigDecimal unitPrice;

    private Boolean selected;

    private List<String> eligibleStates;
}