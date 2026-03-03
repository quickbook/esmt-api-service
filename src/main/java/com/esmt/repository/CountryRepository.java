package com.esmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esmt.model.DmnCountry;


public interface CountryRepository extends JpaRepository<DmnCountry,Long> {
	 
	Optional<DmnCountry> findByCode(String countryCode); 
	List<DmnCountry>  findAllOrderByNameAsc(); 
}
