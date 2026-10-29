package com.opspilot.operations.equipment.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class EquipmentRepositoryTest {

        @Container
        @ServiceConnection
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.11");

        @Autowired
        private EquipmentRepository repository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Autowired
        private TestEntityManager entityManager;

        @Test
        void savesAndFindsEquipmentOnlyInItsOrganization() {
                UUID organizationId = UUID.randomUUID();
                UUID otherOrganizationId = UUID.randomUUID();

                jdbcTemplate.update(
                                """
                                                INSERT INTO organizations (id, name)
                                                VALUES (?, ?), (?, ?)
                                                """,
                                organizationId, "Example Operations",
                                otherOrganizationId, "Other Organization");

                UUID equipmentId = UUID.randomUUID();
                Instant createdAt = Instant.parse("2026-10-29T10:00:00Z");

                EquipmentEntity equipment = new EquipmentEntity(
                                equipmentId,
                                organizationId,
                                "A-105",
                                "Compressor A-105",
                                createdAt);

                repository.saveAndFlush(equipment);
                entityManager.clear();

                var result = repository.findByIdAndOrganizationId(
                                equipmentId,
                                organizationId);

                assertThat(result).isPresent();

                EquipmentEntity loaded = result.orElseThrow();

                assertThat(loaded.getId()).isEqualTo(equipmentId);
                assertThat(loaded.getOrganizationId()).isEqualTo(organizationId);
                assertThat(loaded.getCode()).isEqualTo("A-105");
                assertThat(loaded.getName()).isEqualTo("Compressor A-105");
                assertThat(loaded.getCreatedAt()).isEqualTo(createdAt);

                assertThat(repository.findByIdAndOrganizationId(
                                equipmentId,
                                otherOrganizationId)).isEmpty();

        }

        private UUID createOrganization(String name) {
                UUID id = UUID.randomUUID();

                jdbcTemplate.update(
                                "INSERT INTO organizations (id, name) VALUES (?, ?)",
                                id,
                                name);

                return id;
        }

        private EquipmentEntity newEquipment(UUID organizationId, String code) {
                return new EquipmentEntity(
                                UUID.randomUUID(),
                                organizationId,
                                code,
                                "Test Compressor",
                                Instant.parse("2026-10-29T10:00:00Z"));
        }

        @Test
        void rejectsDuplicateCodeWithinSameOrganization() {
                UUID organizationId = createOrganization("Example Operations");

                repository.saveAndFlush(
                                newEquipment(organizationId, "A-105"));

                EquipmentEntity duplicate = newEquipment(
                                organizationId,
                                "A-105");

                assertThatThrownBy(() -> repository.saveAndFlush(duplicate))
                                .isInstanceOf(DataIntegrityViolationException.class)
                                .hasStackTraceContaining("uq_equipment_organization_code");
        }

        @Test
        void allowsSameCodeInDifferentOrganizations() {
                UUID firstOrganizationId = createOrganization("First Organization");
                UUID secondOrganizationId = createOrganization("Second Organization");

                EquipmentEntity first = newEquipment(firstOrganizationId, "A-105");
                EquipmentEntity second = newEquipment(secondOrganizationId, "A-105");

                repository.saveAndFlush(first);
                repository.saveAndFlush(second);

                entityManager.clear();

                assertThat(repository.findByIdAndOrganizationId(
                                first.getId(),
                                firstOrganizationId)).isPresent();

                assertThat(repository.findByIdAndOrganizationId(
                                second.getId(),
                                secondOrganizationId)).isPresent();
        }
}