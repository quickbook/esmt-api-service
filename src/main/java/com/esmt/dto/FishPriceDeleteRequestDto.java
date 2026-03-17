package com.esmt.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FishPriceDeleteRequestDto {

    @NotEmpty(message = "ids list is required")
    private List<@NotNull(message = "id is required") Long> ids;
}
