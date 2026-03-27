package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esmt.model.DmnPondPurpose;

@Repository
public interface PondPurposeRepository extends JpaRepository<DmnPondPurpose, Long> {
	List<DmnPondPurpose> findByIsActiveTrueOrderByIdAsc();
}