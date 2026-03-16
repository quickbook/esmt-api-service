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
public class QuotePackageOption {

    private String packageCode;
    private String packageName;
    private String description;
    private Boolean preferredOption;

    private BigDecimal estimatedPrice;

    private List<QuoteFishItem> fishItems;
}