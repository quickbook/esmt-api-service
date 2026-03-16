package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.FishPriceMaster;

public interface FishPriceRepository extends JpaRepository<FishPriceMaster, Long> {

List<FishPriceMaster> findByFishTypeAndActiveTrue(String fishType);

}
