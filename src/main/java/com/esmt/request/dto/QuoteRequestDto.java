package com.esmt.request.dto;

import java.math.BigDecimal;

import com.esmt.enums.PondCondition;
import com.esmt.enums.QuoteType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuoteRequestDto {

    @NotBlank(message = "fullName is required")
    private String fullName;

    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    private String address;

    private Integer hearAboutUsCode;

    @NotNull(message = "pondSurfaceAcres is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "pondSurfaceAcres must be greater than 0")
    private BigDecimal pondSurfaceAcres;

    @Min(value = 0, message = "distanceFromLonokeMiles must be 0 or greater")
    private Integer distanceFromLonokeMiles;

    private Integer pondAccessCode;

    @NotNull(message = "pondCondition is required")
    private PondCondition pondCondition;   

    @NotNull(message = "quoteType is required")
    private QuoteType quoteType;
}
