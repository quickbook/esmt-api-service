package com.esmt.service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.esmt.cache.CacheNames;
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
import com.esmt.request.dto.FishPriceCreateDto;
import com.esmt.request.dto.FishPriceUpdateDto;
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
    public FishPriceViewDto create(FishPriceCreateDto dto) {
    	
    	 String variant = dto.getVariant() != null ? dto.getVariant() : "DEFAULT";

    	    boolean exists = fishPriceRepository
    	            .existsByFishType_IdAndFishSize_IdAndUnitType_IdAndVariantAndIsActiveTrue(
    	                    dto.getFishTypeId(),
    	                    dto.getFishSizeId(),
    	                    dto.getUnitTypeId(),
    	                    variant
    	            );

    	    if (exists) {
    	        throw new IllegalStateException(
    	                "Active price already exists for selected fish type, size, unit and variant"
    	        );
    	    }
    	    
        FishPriceMaster entity = map(dto);
        fishPriceRepository.save(entity);
        saveAudit(entity, null, entity.getPrice(), null, entity.getIsActive(), "INSERT");
         return  mapViewDto(entity);
    
    }

	@Cacheable(cacheNames = CacheNames.FISH_PRICES_ALL)
	@Transactional(readOnly = true)
	public List<FishPriceViewDto> getAll() {

		List<String> variantOrder = List.of("DEFAULT", "F1_FLORIDA", "PREMIUM", "GIANT");

		List<FishPriceViewDto> list = fishPriceRepository.findByIsActiveTrue().stream().map(this::mapViewDto)
				.sorted(Comparator
						// 1. Fish Type
						.comparing(FishPriceViewDto::getFishType)

						// 2. Variant (custom order)
						.thenComparing(dto -> {
							int index = variantOrder.indexOf(dto.getVariant());
							return index == -1 ? Integer.MAX_VALUE : index;
						})

						// 3. Unit (fish first, then pound)
						.thenComparing(FishPriceViewDto::getUnitTypeId)

						// 4. Size (by ID ensures correct order)
						.thenComparing(FishPriceViewDto::getFishSizeId))
				.toList();

		// ✅ Assign displayOrder dynamically
		AtomicInteger counter = new AtomicInteger(1);
		list.forEach(item -> item.setDisplayOrder(counter.getAndIncrement()));

		return list;
	}
    @Transactional
    @CacheEvict(cacheNames = CacheNames.FISH_PRICES_ALL, allEntries = true)
    public List<FishPriceViewDto> update(List<FishPriceUpdateDto> dtos) {
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
    public FishPriceViewDto update(Long id, FishPriceUpdateDto dto) {
        return updateOne(id, dto);
    }

    private FishPriceViewDto updateOne(Long id, FishPriceUpdateDto dto) {

        // ✅ Validate path ID vs DTO ID
        if (!id.equals(dto.getId())) {
            throw new IllegalArgumentException("Path ID and DTO ID do not match");
        }

        FishPriceMaster entity = fishPriceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fish price not found with id: " + id));

        var oldPrice = entity.getPrice();
        var oldActive = entity.getIsActive();

        // ✅ Update Fish Type
        if (dto.getFishTypeId() != null) {
            entity.setFishType(findFishTypeById(dto.getFishTypeId()));
        }

        // ✅ Update Fish Size
        if (dto.getFishSizeId() != null) {
            entity.setFishSize(findFishSizeById(dto.getFishSizeId()));
        }

        // ✅ Update Unit Type
        if (dto.getUnitTypeId() != null) {
            entity.setUnitType(findUnitTypeById(dto.getUnitTypeId()));
        }

        // ✅ Update Price
        entity.setPrice(dto.getPrice());

        // ✅ Update Variant
        entity.setVariant(dto.getVariant());

        // ✅ Update Active flag (handle null safely)
        if (dto.getActive() != null) {
            entity.setIsActive(dto.getActive());
        }
  

        fishPriceRepository.save(entity);

        // ✅ Audit
        saveAudit(entity, oldPrice, entity.getPrice(), oldActive, entity.getIsActive(), "UPDATE");

        return mapViewDto(entity);
    }
    private DmnFishTypes findFishTypeById(Long id) {
        return fishTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid fish type id: " + id));
    }

    private DmnFishSize findFishSizeById(Long id) {
        return fishSizeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid fish size id: " + id));
    }

    private DmnUnitType findUnitTypeById(Long id) {
        return unitTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Invalid unit type id: " + id));
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

    private FishPriceMaster map(FishPriceCreateDto dto) {
        FishPriceMaster entity = new FishPriceMaster();
 

        // ✅ Update Fish Type
        if (dto.getFishTypeId() != null) {
            entity.setFishType(findFishTypeById(dto.getFishTypeId()));
        }

        // ✅ Update Fish Size
        if (dto.getFishSizeId() != null) {
            entity.setFishSize(findFishSizeById(dto.getFishSizeId()));
        }

        // ✅ Update Unit Type
        if (dto.getUnitTypeId() != null) {
            entity.setUnitType(findUnitTypeById(dto.getUnitTypeId()));
        }

        // ✅ Update Price
        entity.setPrice(dto.getPrice());

        // ✅ Update Variant
        entity.setVariant(dto.getVariant());

       
            entity.setIsActive(true);
      
        return entity;
    }



    private FishPriceViewDto mapViewDto(FishPriceMaster e) {

        String unitName = e.getUnitType() != null ? e.getUnitType().getName() : null;
        String unitText = unitName != null ? "price per " + unitName.toLowerCase() : null;

        FishPriceViewDto view = new FishPriceViewDto();

        // ✅ Primary ID (VERY IMPORTANT for UI)
        view.setId(e.getId());

        // ✅ Fish Type
        if (e.getFishType() != null) {
            view.setFishTypeId(e.getFishType().getId());
            view.setFishType(e.getFishType().getName());
        }

        // ✅ Fish Size
        if (e.getFishSize() != null) {
            view.setFishSizeId(e.getFishSize().getId());
            view.setSizeLabel(e.getFishSize().getName());
        }

        // ✅ Unit Type
        if (e.getUnitType() != null) {
            view.setUnitTypeId(e.getUnitType().getId());
            view.setUnitType(unitText);
        }

        // ✅ Price & Variant
        view.setPrice(e.getPrice());
        view.setVariant(e.getVariant());  

        view.setDisplayOrder(e.getId() != null ? e.getId().intValue() : null);

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
