package com.esmt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.esmt.dto.FishPriceDto;
import com.esmt.model.FishPriceMaster;
import com.esmt.repository.FishPriceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FishPriceService {

    private final FishPriceRepository repository;

    public FishPriceDto create(FishPriceDto dto) {

        FishPriceMaster entity = map(dto);

        repository.save(entity);

        dto.setId(entity.getId());

        return dto;
    }

    public List<FishPriceDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::mapDto)
                .toList();
    }

    public FishPriceDto update(Long id, FishPriceDto dto) {

        FishPriceMaster entity =
                repository.findById(id)
                        .orElseThrow();

        entity.setFishType(dto.getFishType());
        entity.setSizeLabel(dto.getSizeLabel());
        entity.setUnitType(dto.getUnitType());
        entity.setPrice(dto.getPrice());
        entity.setVariant(dto.getVariant());
        entity.setActive(dto.getActive());

        repository.save(entity);

        return mapDto(entity);
    }

    private FishPriceMaster map(FishPriceDto dto) {

        FishPriceMaster e = new FishPriceMaster();

        e.setFishType(dto.getFishType());
        e.setSizeLabel(dto.getSizeLabel());
        e.setUnitType(dto.getUnitType());
        e.setPrice(dto.getPrice());
        e.setVariant(dto.getVariant());
        e.setActive(dto.getActive());

        return e;
    }

    private FishPriceDto mapDto(FishPriceMaster e) {

        FishPriceDto d = new FishPriceDto();

        d.setId(e.getId());
        d.setFishType(e.getFishType());
        d.setSizeLabel(e.getSizeLabel());
        d.setUnitType(e.getUnitType());
        d.setPrice(e.getPrice());
        d.setVariant(e.getVariant());
        d.setActive(e.getActive());

        return d;
    }
}