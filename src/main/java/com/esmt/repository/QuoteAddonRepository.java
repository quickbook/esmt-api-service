package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esmt.model.QuoteAddonMaster;

@Repository
public interface QuoteAddonRepository extends JpaRepository<QuoteAddonMaster, Long> {

    // ✅ Main method used in service
    List<QuoteAddonMaster> findByQuoteTypeAndIsActiveTrue(String quoteType);

}