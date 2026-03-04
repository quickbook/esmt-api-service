package com.esmt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dmn_fish_size")
public class DmnFishSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
