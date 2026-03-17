package com.esmt.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.request.dto.QuoteRequestDto;
import com.esmt.response.dto.QuoteEstimateResponse;
import com.esmt.service.QuoteApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/esmt/api/v1/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteApplicationService service;

    @PostMapping("/estimate")
    public QuoteEstimateResponse estimate(
            @Valid @RequestBody QuoteRequestDto dto) {

        return service.preview(dto);
    }

    @PostMapping("/finalize")
    public QuoteEstimateResponse finalizeQuote(
            @Valid @RequestBody QuoteRequestDto dto) {

        return service.finalizeQuote(dto);
    }
}
