package com.esmt.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FishPriceDto {

    private Long id;

    @NotBlank(message = "fishType is required")
    private String fishType;

    @NotBlank(message = "sizeLabel is required")
    private String sizeLabel;

    @NotBlank(message = "unitType is required")
    private String unitType;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be greater than 0")
    private BigDecimal price;

    @Size(max = 50, message = "variant must be at most 50 characters")
    private String variant;

    private Boolean active;
}