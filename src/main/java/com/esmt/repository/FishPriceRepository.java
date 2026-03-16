package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import com.esmt.model.FishPriceMaster;
import com.esmt.model.DmnFishTypes;

public interface FishPriceRepository extends JpaRepository<FishPriceMaster, Long> {

	@EntityGraph(attributePaths = {"fishType", "fishSize", "unitType"})
	List<FishPriceMaster> findByIsActiveTrue();

	List<FishPriceMaster> findByFishTypeAndIsActiveTrue(DmnFishTypes fishType);

}
