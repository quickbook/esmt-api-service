package com.esmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.esmt.model.ApiLog;
 

@Repository
public interface ApiLogRepository extends JpaRepository<ApiLog, Long> { }