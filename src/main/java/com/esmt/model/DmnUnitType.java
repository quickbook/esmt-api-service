package com.esmt.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "dmn_unit_type")
public class DmnUnitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", length = 20)
    private String name;
}
