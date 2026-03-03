package com.esmt.controller;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/esmt/health")
public class HealthCheckController {
	
	
	 
	
	@Autowired
	private DataSource dataSource;

	@GetMapping("/dbCheck")
	public ResponseEntity<Map<String, Object>> healthCheck() {

	    Map<String, Object> response = new HashMap<>();

	    try (Connection connection = dataSource.getConnection()) {
	        if (connection.isValid(2)) {
	            response.put("database", "UP");
	        }
	    } catch (Exception e) {
	        response.put("database", "DOWN");
	    }

	    response.put("status", "UP");
	    response.put("timestamp", LocalDateTime.now());

	    return ResponseEntity.ok(response);
	}
	
}
