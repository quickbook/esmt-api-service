package com.esmt.request.dto;

import java.math.BigDecimal;

import com.esmt.enums.PondCondition;
import com.esmt.enums.QuoteType;

import lombok.Data;

@Data
public class QuoteRequestDto {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;

    private Integer hearAboutUsCode;

    private BigDecimal pondSurfaceAcres;
    private Integer distanceFromLonokeMiles;

    private Integer pondAccessCode;

    private PondCondition pondCondition;   

    private QuoteType quoteType;
}
