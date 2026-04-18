package com.esmt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.esmt.response.dto.ApiResponse;
import com.esmt.service.PricingRuleService;
import com.esmt.dto.MarkupDto;
import com.esmt.dto.FreightDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/pricing-rules")
@RequiredArgsConstructor
public class PricingRuleController {

    private final PricingRuleService pricingRuleService;

    // =========================
    // MARKUP APIs
    // =========================

    @GetMapping("/markup")
    public ApiResponse<List<MarkupDto>> getMarkup() {
        return ApiResponse.success(pricingRuleService.getAllMarkup());
    }

    @PostMapping("/markup")
    public ApiResponse<MarkupDto> createMarkup(@Valid @RequestBody MarkupDto dto) {
        return ApiResponse.success("Markup created successfully",
                pricingRuleService.createMarkup(dto));
    }

    @PutMapping("/markup")
    public ApiResponse<List<MarkupDto>> updateMarkup(@Valid @RequestBody List<MarkupDto> dtos) {
        return ApiResponse.success("Markup updated successfully",
                pricingRuleService.updateMarkup(dtos));
    }

    @DeleteMapping("/markup")
    public ApiResponse<Void> deleteMarkup(@RequestBody List<Long> ids) {
        pricingRuleService.deleteMarkup(ids);
        return ApiResponse.success(null);
    }

    // =========================
    // FREIGHT APIs
    // =========================

    @GetMapping("/freight")
    public ApiResponse<List<FreightDto>> getFreight() {
        return ApiResponse.success(pricingRuleService.getAllFreight());
    }

    @PostMapping("/freight")
    public ApiResponse<FreightDto> createFreight(@Valid @RequestBody FreightDto dto) {
        return ApiResponse.success("Freight created successfully",
                pricingRuleService.createFreight(dto));
    }

    @PutMapping("/freight")
    public ApiResponse<List<FreightDto>> updateFreight(@Valid @RequestBody List<FreightDto> dtos) {
        return ApiResponse.success("Freight updated successfully",
                pricingRuleService.updateFreight(dtos));
    }

    @DeleteMapping("/freight")
    public ApiResponse<Void> deleteFreight(@RequestBody List<Long> ids) {
        pricingRuleService.deleteFreight(ids);
        return ApiResponse.success(null);
    }
}