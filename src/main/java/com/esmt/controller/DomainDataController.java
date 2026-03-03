package com.esmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esmt.dto.DomainDataDTO;
import com.esmt.service.DomainDataService;

@RestController
@RequestMapping("/esmt/api/domain")
public class DomainDataController {
	

	@Autowired
    private DomainDataService domainDataService;
	//Ex : /esmt/api/domain/lead-source , /esmt/api/domain/countries
	@GetMapping("/{domainName}")
    public ResponseEntity<List<DomainDataDTO>> getDomainData(
            @PathVariable String domainName) {

        return ResponseEntity.ok(domainDataService.getDomainData(domainName));
    }

}
