package com.esmt.request.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FishPriceUpdateDto {

	@NotNull(message = "Id is required for updating fish price")
	private Long id;

	@NotNull(message = "Fish type is required")
	private Long fishTypeId;

	@NotNull(message = "Fish size is required")
	private Long fishSizeId;

	@NotNull(message = "Unit type is required")
	private Long unitTypeId;

	@NotNull(message = "Price is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal price;

	@NotNull(message = "Variant is required")
	@Size(max = 15, message = "Variant must not exceed 15 characters")
	private String variant;

	private Boolean active;
}
