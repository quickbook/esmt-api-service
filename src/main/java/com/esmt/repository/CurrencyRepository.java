package com.esmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.DmnCurrency;

 

public interface CurrencyRepository extends JpaRepository<DmnCurrency, String> { 
}