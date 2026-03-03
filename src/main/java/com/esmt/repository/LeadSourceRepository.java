package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esmt.model.LeadSource;

@Repository
public interface LeadSourceRepository extends JpaRepository<LeadSource, Long> {

    List<LeadSource> findByIsActiveTrueOrderByNameAsc();
}
