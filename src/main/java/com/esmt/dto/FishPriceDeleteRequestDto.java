package com.esmt.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
 
import lombok.Data;

@Data
public class FishPriceDeleteRequestDto {

   
	@NotNull(message = "Id is required")
	@NotEmpty(message = "At least one id must be provided")
	private List<@NotNull(message = "id is required") Long> ids;
}
