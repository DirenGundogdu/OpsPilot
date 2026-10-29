package com.opspilot.operations.equipment.persistence;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table (name = "equipment")
public class EquipmentEntity {

    @Id 
    @Column(nullable = false,updatable = false)
    private UUID id;

    @Column(name = "organization_id", nullable = false, updatable = false)
    private UUID organizationId;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected EquipmentEntity() {
        // Required by JPA.
    }

    public EquipmentEntity(
            UUID id,
            UUID organizationId,
            String code,
            String name,
            Instant createdAt
    ) {
        this.id = id;
        this.organizationId = organizationId;
        this.code = code;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    
   
}
