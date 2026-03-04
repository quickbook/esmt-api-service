package com.esmt.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.DomainDataDTO;
import com.esmt.service.DomainDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/esmt/api/domain")
public class DomainDataController {
	

    private final DomainDataService domainDataService;
	@GetMapping("/{domainName}")
    public ResponseEntity<List<DomainDataDTO>> getDomainData(
            @PathVariable String domainName) {

        return ResponseEntity.ok(domainDataService.getDomainData(domainName));
    }

}
