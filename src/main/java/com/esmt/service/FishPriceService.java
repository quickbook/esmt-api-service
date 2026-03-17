package com.esmt.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmt.cache.CacheNames;
import com.esmt.dto.FishPriceDto;
import com.esmt.dto.FishPriceViewDto;
import com.esmt.model.DmnFishSize;
import com.esmt.model.DmnFishTypes;
import com.esmt.model.DmnUnitType;
import com.esmt.model.FishPriceMaster;
import com.esmt.model.FishPriceMasterAudit;
import com.esmt.repository.FishPriceMasterAuditRepository;
import com.esmt.repository.FishPriceRepository;
import com.esmt.repository.FishSizeRepository;
import com.esmt.repository.FishTypeRepository;
import com.esmt.repository.UnitTypeRepository;
import com.esmt.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FishPriceService {

    private final FishPriceRepository fishPriceRepository;
    private final FishPriceMasterAuditRepository fishPriceMasterAuditRepository;
    private final FishTypeRepository fishTypeRepository;
    private final FishSizeRepository fishSizeRepository;
    private final UnitTypeRepository unitTypeRepository;

    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public FishPriceDto create(FishPriceDto dto) {
        FishPriceMaster entity = map(dto);
        fishPriceRepository.save(entity);
        saveAudit(entity, null, entity.getPrice(), null, entity.getIsActive(), "INSERT");
        dto.setId(entity.getId());
        return dto;
    }

    @Cacheable(cacheNames = CacheNames.FISH_PRICES_ALL)
    @Transactional(readOnly = true)
    public List<FishPriceViewDto> getAll() {
        return fishPriceRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapViewDto)
                .toList();
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public List<FishPriceDto> update(List<FishPriceDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw new IllegalArgumentException("At least one fish price item is required for update");
        }

        return dtos.stream()
                .map(dto -> {
                    if (dto.getId() == null) {
                        throw new IllegalArgumentException("id is required for update");
                    }
                    return updateOne(dto.getId(), dto);
                })
                .toList();
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public FishPriceDto update(Long id, FishPriceDto dto) {
        return updateOne(id, dto);
    }

    private FishPriceDto updateOne(Long id, FishPriceDto dto) {
        FishPriceMaster entity = fishPriceRepository.findById(id).orElseThrow();
        var oldPrice = entity.getPrice();
        var oldActive = entity.getIsActive();

        if (dto.getFishType() != null) {
            entity.setFishType(findFishType(dto.getFishType()));
        }

        if (dto.getSizeLabel() != null) {
            entity.setFishSize(findFishSize(dto.getSizeLabel()));
        }

        if (dto.getUnitType() != null) {
            entity.setUnitType(findUnitType(dto.getUnitType()));
        }

        entity.setPrice(dto.getPrice());
        entity.setVariant(dto.getVariant());
        entity.setIsActive(dto.getActive());

        fishPriceRepository.save(entity);
        saveAudit(entity, oldPrice, entity.getPrice(), oldActive, entity.getIsActive(), "UPDATE");
        return mapDto(entity);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public void delete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("At least one fish price item is required for delete");
        }

        ids.forEach(id -> {
            if (id == null) {
                throw new IllegalArgumentException("id is required for delete");
            }
            deleteOne(id);
        });
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public void delete(Long id) {
        deleteOne(id);
    }

    private void deleteOne(Long id) {
        FishPriceMaster entity = fishPriceRepository.findById(id).orElseThrow();

        if (!Boolean.TRUE.equals(entity.getIsActive())) {
            return;
        }

        entity.setIsActive(false);
        fishPriceRepository.save(entity);
        saveAudit(entity, entity.getPrice(), entity.getPrice(), true, false, "DELETE");
    }

    private void saveAudit(
            FishPriceMaster entity,
            java.math.BigDecimal oldPrice,
            java.math.BigDecimal newPrice,
            Boolean oldActive,
            Boolean newActive,
            String actionType) {

        FishPriceMasterAudit audit = new FishPriceMasterAudit();
        audit.setFishPriceId(entity.getId());
        audit.setFishTypeId(entity.getFishType() != null ? entity.getFishType().getId() : null);
        audit.setFishSizeId(entity.getFishSize() != null ? entity.getFishSize().getId() : null);
        audit.setUnitTypeId(entity.getUnitType() != null ? entity.getUnitType().getId() : null);
        audit.setVariant(entity.getVariant());
        audit.setOldPrice(oldPrice);
        audit.setNewPrice(newPrice);
        audit.setOldActive(oldActive);
        audit.setNewActive(newActive);
        audit.setActionType(actionType);
        audit.setChangedBy(CommonUtil.getCurrentUsername());
        fishPriceMasterAuditRepository.save(audit);
    }

    private FishPriceMaster map(FishPriceDto dto) {
        FishPriceMaster e = new FishPriceMaster();

        if (dto.getFishType() != null) {
            e.setFishType(findFishType(dto.getFishType()));
        }

        if (dto.getSizeLabel() != null) {
            e.setFishSize(findFishSize(dto.getSizeLabel()));
        }

        if (dto.getUnitType() != null) {
            e.setUnitType(findUnitType(dto.getUnitType()));
        }

        e.setPrice(dto.getPrice());
        e.setVariant(dto.getVariant());
        e.setIsActive(dto.getActive());

        return e;
    }

    private FishPriceDto mapDto(FishPriceMaster e) {
        FishPriceDto d = new FishPriceDto();

        d.setId(e.getId());
        d.setFishType(e.getFishType() != null ? e.getFishType().getName() : null);
        d.setSizeLabel(e.getFishSize() != null ? e.getFishSize().getName() : null);
        d.setUnitType(e.getUnitType() != null ? e.getUnitType().getName() : null);
        d.setPrice(e.getPrice());
        d.setVariant(e.getVariant());
        d.setActive(e.getIsActive());

        return d;
    }

    private FishPriceViewDto mapViewDto(FishPriceMaster e) {
        String unitName = e.getUnitType() != null ? e.getUnitType().getName() : null;
        String unitText = unitName != null ? "price per " + unitName.toLowerCase() : null;

        FishPriceViewDto view = new FishPriceViewDto();
        view.setSize(e.getFishSize() != null ? e.getFishSize().getName() : null);
        view.setFishType(e.getFishType() != null ? e.getFishType().getName() : null);
        view.setPrice(e.getPrice());
        view.setUnitText(unitText);
        view.setDisplayOrder(e.getId() != null ? e.getId().intValue() : null);
        view.setVariant(e.getVariant());
        return view;
    }

    private DmnFishTypes findFishType(String name) {
        return fishTypeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Fish type not found: " + name));
    }

    private DmnFishSize findFishSize(String name) {
        return fishSizeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Fish size not found: " + name));
    }

    private DmnUnitType findUnitType(String name) {
        return unitTypeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Unit type not found: " + name));
    }
}
