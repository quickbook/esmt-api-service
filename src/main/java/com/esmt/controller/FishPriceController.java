package com.esmt.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.FishPriceDto;
import com.esmt.dto.FishPriceDeleteRequestDto;
import com.esmt.dto.FishPriceViewDto;
import com.esmt.service.FishPriceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/fish-prices")
@RequiredArgsConstructor
public class FishPriceController {

    private final FishPriceService fishPriceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FishPriceDto create(@Valid @RequestBody FishPriceDto dto) {
        return fishPriceService.create(dto);
    }

    @GetMapping
    public List<FishPriceViewDto> getAll() {
        return fishPriceService.getAll();
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<FishPriceDto> update(
            @RequestBody List<@Valid FishPriceDto> dtos) {

        return fishPriceService.update(dtos);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@Valid @RequestBody FishPriceDeleteRequestDto request) {
        fishPriceService.delete(request.getIds());
    }
}