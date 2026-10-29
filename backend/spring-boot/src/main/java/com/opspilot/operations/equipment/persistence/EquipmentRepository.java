package com.opspilot.operations.equipment.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, UUID> {

    Optional<EquipmentEntity> findByIdAndOrganizationId(UUID id, UUID organizationId);

}