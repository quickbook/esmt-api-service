package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.FishPriceMarkup;

public interface FishPriceMarkupRepository extends JpaRepository<FishPriceMarkup, Long> {

    List<FishPriceMarkup> findByIsActiveTrueOrderByMinAcresDesc();

}