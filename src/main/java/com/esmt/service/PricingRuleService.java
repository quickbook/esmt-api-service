package com.esmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esmt.dto.FreightDto;
import com.esmt.dto.MarkupDto;
import com.esmt.model.FishFreightRate;
import com.esmt.model.FishPriceMarkup;
import com.esmt.repository.FishFreightRateRepository;
import com.esmt.repository.FishPriceMarkupRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PricingRuleService{

    private final FishPriceMarkupRepository markupRepo;
    private final FishFreightRateRepository freightRepo;

    // =========================
    // MARKUP
    // =========================

    
    @Transactional
    public List<MarkupDto> getAllMarkup() {
        return markupRepo.findByIsActiveTrueOrderByMinAcresDesc()
                .stream()
                .map(this::mapMarkupDto)
                .toList();
    }
 
    public MarkupDto createMarkup(MarkupDto dto) {
        FishPriceMarkup entity = new FishPriceMarkup();
        entity.setMinAcres(dto.getMinAcres());
        entity.setMaxAcres(dto.getMaxAcres());
        entity.setMultiplier(dto.getMultiplier());
        entity.setIsActive(true);

        return mapMarkupDto(markupRepo.save(entity));
    }

     
    public List<MarkupDto> updateMarkup(List<MarkupDto> dtos) {
        return dtos.stream().map(dto -> {
            FishPriceMarkup entity = markupRepo.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Markup not found"));

            entity.setMinAcres(dto.getMinAcres());
            entity.setMaxAcres(dto.getMaxAcres());
            entity.setMultiplier(dto.getMultiplier());

            return mapMarkupDto(entity);
        }).toList();
    }

  
    public void deleteMarkup(List<Long> ids) {
        List<FishPriceMarkup> list = markupRepo.findAllById(ids);
        list.forEach(e -> e.setIsActive(false));
    }

    // =========================
    // FREIGHT
    // =========================

  
    @Transactional
    public List<FreightDto> getAllFreight() {
        return freightRepo.findByIsActiveTrue()
                .stream()
                .map(this::mapFreightDto)
                .toList();
    }

   
    public FreightDto createFreight(FreightDto dto) {

        freightRepo.findByVehicleTypeAndIsActiveTrue(dto.getVehicleType())
                .ifPresent(e -> {
                    throw new RuntimeException("Vehicle type already exists");
                });

        FishFreightRate entity = new FishFreightRate();
        entity.setVehicleType(dto.getVehicleType());
        entity.setRatePerMile(dto.getRatePerMile());
        entity.setIsActive(true);

        return mapFreightDto(freightRepo.save(entity));
    }

     
    public List<FreightDto> updateFreight(List<FreightDto> dtos) {
        return dtos.stream().map(dto -> {
            FishFreightRate entity = freightRepo.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Freight not found"));

            entity.setRatePerMile(dto.getRatePerMile());
            return mapFreightDto(entity);
        }).toList();
    }

    
    public void deleteFreight(List<Long> ids) {
        List<FishFreightRate> list = freightRepo.findAllById(ids);
        list.forEach(e -> e.setIsActive(false));
    }

    // =========================
    // MAPPERS
    // =========================

    private MarkupDto mapMarkupDto(FishPriceMarkup e) {
        MarkupDto dto = new MarkupDto();
        dto.setId(e.getId());
        dto.setMinAcres(e.getMinAcres());
        dto.setMaxAcres(e.getMaxAcres());
        dto.setMultiplier(e.getMultiplier());
        dto.setActive(e.getIsActive());
        return dto;
    }

    private FreightDto mapFreightDto(FishFreightRate e) {
        FreightDto dto = new FreightDto();
        dto.setId(e.getId());
        dto.setVehicleType(e.getVehicleType());
        dto.setRatePerMile(e.getRatePerMile());
        dto.setActive(e.getIsActive());
        return dto;
    }
}