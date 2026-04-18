package com.esmt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.FishPriceDeleteRequestDto;
import com.esmt.dto.FishPriceViewDto;
import com.esmt.request.dto.FishPriceCreateDto;
import com.esmt.request.dto.FishPriceUpdateDto;
import com.esmt.response.dto.ApiResponse;
import com.esmt.service.FishPriceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/fish-prices")
@RequiredArgsConstructor
public class FishPriceController {

    private final FishPriceService fishPriceService;

    @PostMapping
  //  @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<FishPriceViewDto> create(@Valid @RequestBody FishPriceCreateDto dto) {   
        return ApiResponse.success("Fish price created successfully", fishPriceService.create(dto));
    }

    @GetMapping
    public ApiResponse<List<FishPriceViewDto>> getAll() {
    	 return ApiResponse.success(fishPriceService.getAll()); 
    }

    @PutMapping
   // @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<FishPriceViewDto>> update(
    		@Valid @RequestBody List<FishPriceUpdateDto> dtos) {
        return ApiResponse.success("Fish prices updated successfully", fishPriceService.update(dtos));
    }

    @DeleteMapping
    public ApiResponse<Void> delete(
            @Valid @RequestBody FishPriceDeleteRequestDto request) {

        fishPriceService.delete(request.getIds());
        return ApiResponse.success(null);
    }
}