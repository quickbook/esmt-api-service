package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esmt.model.DmnLeadSource;

@Repository
public interface LeadSourceRepository extends JpaRepository<DmnLeadSource, Long> {

    List<DmnLeadSource> findByIsActiveTrueOrderByNameAsc();
}
