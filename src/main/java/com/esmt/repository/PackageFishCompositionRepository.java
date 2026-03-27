package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.PackageFishComposition;

public interface PackageFishCompositionRepository extends JpaRepository<PackageFishComposition, Long> {
    List<PackageFishComposition> findByQuoteTypeAndPackageCodeAndIsActiveTrue(String quoteType, String packageCode);
}
