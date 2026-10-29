package com.opspilot.operations.equipment.application;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.opspilot.operations.equipment.domain.EquipmentDetails;
import com.opspilot.operations.equipment.persistence.EquipmentEntity;
import com.opspilot.operations.equipment.persistence.EquipmentRepository;

import jakarta.transaction.Transactional;

@Service 
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final Clock clock;

    public EquipmentService( EquipmentRepository equipmentRepository, Clock clock) {
        this.equipmentRepository = equipmentRepository;
        this.clock = clock;
    }

    @Transactional 
    public CreatedEquipment create(
        UUID organizationId,
        String code,
        String name
    ) {
        if (organizationId == null) {
            throw new IllegalArgumentException("Organization ID is required");
        }

        EquipmentDetails details = new EquipmentDetails(code, name);

        EquipmentEntity entity = new EquipmentEntity(
                UUID.randomUUID(),
                organizationId,
                details.code(),
                details.name(),
                clock.instant()
        );

        EquipmentEntity saved = equipmentRepository.save(entity);

        return new CreatedEquipment(
                saved.getId(),
                saved.getOrganizationId(),
                saved.getCode(),
                saved.getName(),
                saved.getCreatedAt()
        );
    }

    public record CreatedEquipment(
        UUID id,
        UUID organizationId,
        String code,
        String name,
        Instant createdAt
    ){}

}
