package com.esmt.controller;

import java.util.List;
import java.util.Map;

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
	
	@GetMapping("/pond-purpose/by-pond-type")
	public ResponseEntity<Map<String, List<DomainDataDTO>>> getPondPurpose() {

		return ResponseEntity.ok(domainDataService.getPondPurpose("pong-purpose-by-pond-type"));
	}

}
