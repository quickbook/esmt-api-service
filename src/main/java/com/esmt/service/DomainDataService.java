package com.esmt.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.esmt.cache.CacheNames;
import com.esmt.dto.DomainDataDTO;
import com.esmt.repository.CountryRepository;
import com.esmt.repository.FishSizeRepository;
import com.esmt.repository.FishTypeRepository;
import com.esmt.repository.LeadSourceRepository;
import com.esmt.repository.PondAccessRepository;
import com.esmt.repository.UnitTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DomainDataService {

    private final LeadSourceRepository leadSourceRepository;
 
	private final CountryRepository countryRepository;
    private final FishTypeRepository fishTypeRepository;
    private final FishSizeRepository fishSizeRepository;
    private final PondAccessRepository pondAccessRepository;
    private final UnitTypeRepository unitTypeRepository;

    @Cacheable(cacheNames = CacheNames.DOMAIN_DATA_BY_NAME, key = "#domainName.toLowerCase()")
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
            case "fish-types":
                return fishTypeRepository.findAllByIsActiveTrueOrderByNameAsc()
                        .stream()
                        .map(ft -> new DomainDataDTO(
                                ft.getId(),
                                ft.getCode(),
                                ft.getName()))
                        .toList();
            case "fish-sizes":
                return fishSizeRepository.findAllByIsActiveTrueOrderByNameAsc()
                        .stream()
                        .map(fs -> new DomainDataDTO(
                                fs.getId(),
                                fs.getCode(),
                                fs.getName()))
                        .toList();
            case "pond-access":
                return pondAccessRepository.findAllByIsActiveTrueOrderByNameAsc()
                        .stream()
                        .map(pa -> new DomainDataDTO(
                                pa.getId(),
                                pa.getCode(),
                                pa.getName()))
                        .toList();
            case "unit-types":
                return unitTypeRepository.findAll()
                        .stream()
                        .map(ut -> new DomainDataDTO(
                                ut.getId(),
                                ut.getCode(),
                                ut.getName()))
                        .toList();

            default:
                throw new IllegalArgumentException("Invalid domain name: " + domainName);
        }
    }
}