package com.esmt.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.FishPriceDto;
import com.esmt.dto.FishPriceViewDto;
import com.esmt.service.FishPriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/fish-prices")
@RequiredArgsConstructor
public class FishPriceController {

    private final FishPriceService fishPriceService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public FishPriceDto create(@RequestBody FishPriceDto dto) {
        return fishPriceService.create(dto);
    }

    @GetMapping
    public List<FishPriceViewDto> getAll() {
        return fishPriceService.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public FishPriceDto update(
            @PathVariable Long id,
            @RequestBody FishPriceDto dto) {

        return fishPriceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        fishPriceService.delete(id);
    }
}