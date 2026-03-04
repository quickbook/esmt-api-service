package com.esmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esmt.dto.DomainDataDTO;
import com.esmt.repository.CountryRepository;
import com.esmt.repository.LeadSourceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DomainDataService {

    private final LeadSourceRepository leadSourceRepository;
	
	private final CountryRepository countryRepository;
 

    public List<DomainDataDTO> getDomainData(String domainName) {

        switch (domainName.toLowerCase()) {

            case "lead-source":
                return leadSourceRepository.findByIsActiveTrueOrderByNameAsc()
                        .stream()
                        .map(ls -> new DomainDataDTO(ls.getId(), String.valueOf(ls.getId()),ls.getName()))
                        .toList();
            case "countries":
                return countryRepository.findAllByOrderByNameAsc()
                        .stream()
                        .map(c -> new DomainDataDTO(
                                c.getId(),
                                c.getCode(),
                                c.getName()))
                        .toList();

            default:
                throw new IllegalArgumentException("Invalid domain name: " + domainName);
        }
    }
}