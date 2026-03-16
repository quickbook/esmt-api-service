package com.esmt.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.FishPriceDto;
import com.esmt.service.FishPriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/fish-prices")
@RequiredArgsConstructor
public class FishPriceController {

    private final FishPriceService service;

    @PostMapping
    public FishPriceDto create(@RequestBody FishPriceDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<FishPriceDto> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public FishPriceDto update(
            @PathVariable Long id,
            @RequestBody FishPriceDto dto) {

        return service.update(id, dto);
    }
}