package com.esmt.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "fish_price_master_audit")
public class FishPriceMasterAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "fish_price_id")
    private Long fishPriceId;

    @Column(name = "fish_type_id")
    private Long fishTypeId;

    @Column(name = "fish_size_id")
    private Long fishSizeId;

    @Column(name = "unit_type_id")
    private Long unitTypeId;

    @Column(name = "variant", length = 50)
    private String variant;

    @Column(name = "old_price", precision = 10, scale = 2)
    private BigDecimal oldPrice;

    @Column(name = "new_price", precision = 10, scale = 2)
    private BigDecimal newPrice;

    @Column(name = "old_active")
    private Boolean oldActive;

    @Column(name = "new_active")
    private Boolean newActive;

    @Column(name = "action_type", length = 20)
    private String actionType;

    @Column(name = "changed_by", length = 100)
    private String changedBy;

    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    protected void onCreate() {
        if (changedAt == null) {
            changedAt = LocalDateTime.now();
        }
    }
}
