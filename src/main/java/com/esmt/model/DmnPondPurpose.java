package com.esmt.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity 
@Table(name = "dmn_pond_purpose") 
@Data
public class DmnPondPurpose {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String code;
	
	@Column(name = "pond_type", nullable = false)
	private String pondType;
	
	@Column(name = "is_active", nullable = false)
	private Boolean isActive;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
}
