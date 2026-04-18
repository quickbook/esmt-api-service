package com.esmt.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;

import com.esmt.model.FishPriceMaster;
import com.esmt.model.DmnFishTypes;

public interface FishPriceRepository extends JpaRepository<FishPriceMaster, Long> {

	@EntityGraph(attributePaths = {"fishType", "fishSize", "unitType"})
	List<FishPriceMaster> findByIsActiveTrue();

	List<FishPriceMaster> findByFishTypeAndIsActiveTrue(DmnFishTypes fishType);
	
	@Query("""
		    SELECT p.price FROM FishPriceMaster p
		    WHERE p.fishType.id = :fishTypeId
		    AND p.fishSize.id = :fishSizeId
		    AND p.unitType.id = :unitTypeId
		    AND p.variant = :variant
		    AND p.isActive = true	
		""")
		BigDecimal findActivePrice(Long fishTypeId,
		                           Long fishSizeId,
		                           Long unitTypeId,
		                           String variant);
	
	
	boolean existsByFishType_IdAndFishSize_IdAndUnitType_IdAndVariantAndIsActiveTrue(
	        Long fishTypeId,
	        Long fishSizeId,
	        Long unitTypeId,
	        String variant
	);
	
}
