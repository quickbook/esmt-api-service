package com.esmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esmt.dto.DomainDataDTO;
import com.esmt.repository.CountryRepository;
import com.esmt.repository.LeadSourceRepository;

@Service
public class DomainDataService {

	@Autowired
    private LeadSourceRepository leadSourceRepository;
	
	@Autowired
	private CountryRepository countryRepository;
 

    public List<DomainDataDTO> getDomainData(String domainName) {

        switch (domainName.toLowerCase()) {

            case "lead-source":
                return leadSourceRepository.findByIsActiveTrueOrderByNameAsc()
                        .stream()
                        .map(ls -> new DomainDataDTO(ls.getId(), String.valueOf(ls.getId()),ls.getName()))
                        .toList();
            case "countries":
                return countryRepository.findAllOrderByNameAsc()
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