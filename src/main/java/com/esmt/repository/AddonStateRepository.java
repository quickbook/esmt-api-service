package com.esmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.AddonStateMapping;

public interface AddonStateRepository extends JpaRepository<AddonStateMapping, Long> {

    List<AddonStateMapping> findByAddonId(Long addonId);
}