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

    //This is for the package options section, which will be used to provide recommendations and validation for the package options on the frontend
    private List<QuotePackageOption> packages;
    
    //This is for the add-on options section, which will be used to provide recommendations and validation for the add-on options on the frontend
    private List<QuoteAddonOption> addons;
    
    //This is for the quantity input section, which will be used to provide recommendations and validation for the quantity inputs on the frontend
    private List<QuoteQuantityInput> quantityInputs;
    
    private BigDecimal minimumOrderAmount; 
    
    private List<String> messages; 
    
    private List<String> estMessages; 
}