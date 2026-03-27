package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.QuotePackageMaster;

public interface QuotePackageRepository extends JpaRepository<QuotePackageMaster, Long> {
    List<QuotePackageMaster> findByQuoteTypeAndIsActiveTrue(String quoteType);
}
