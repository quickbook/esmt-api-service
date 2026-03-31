package com.esmt.response.dto;

import java.math.BigDecimal;
import java.util.List;

import com.esmt.dto.QuoteAddonOption;
import com.esmt.dto.QuotePackageOption;
import com.esmt.dto.QuoteQuantityInput;
import com.esmt.enums.QuoteType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteEstimateResponse {

    private QuoteType quoteType;
    
    private String title;
    
    private String description;

    //This is for New and Old
    private List<QuotePackageOption> packages;
    
    //this is only for NEW pond
    private List<QuoteAddonOption> addons;
    
    //this is only for OLD pond
    private List<QuoteQuantityInput> quantityInputs;
    
    private BigDecimal minimumOrderAmount; 
    
    private List<String> infoNotes; 
    
    private List<String> disclaimerNotes; 
    
    private List<String> errors;
    
    private String message;
}