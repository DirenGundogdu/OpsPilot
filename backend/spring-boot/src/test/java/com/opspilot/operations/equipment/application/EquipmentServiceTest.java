package com.opspilot.operations.equipment.application;

import com.opspilot.operations.equipment.persistence.EquipmentEntity;
import com.opspilot.operations.equipment.persistence.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    private static final Instant NOW =
            Instant.parse("2026-10-29T10:00:00Z");

    @Mock
    private EquipmentRepository repository;

    private EquipmentService service;

    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);
        service = new EquipmentService(repository, clock);
    }

    @Test
    void createsEquipmentWithNormalizedDetails() {
        UUID organizationId = UUID.randomUUID();

        when(repository.save(any(EquipmentEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var result = service.create(
                organizationId,
                "  a-105  ",
                "  Compressor A-105  "
        );

        ArgumentCaptor<EquipmentEntity> captor =
                ArgumentCaptor.forClass(EquipmentEntity.class);

        verify(repository).save(captor.capture());

        EquipmentEntity saved = captor.getValue();

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOrganizationId()).isEqualTo(organizationId);
        assertThat(saved.getCode()).isEqualTo("A-105");
        assertThat(saved.getName()).isEqualTo("Compressor A-105");
        assertThat(saved.getCreatedAt()).isEqualTo(NOW);

        assertThat(result).isEqualTo(
                new EquipmentService.CreatedEquipment(
                        saved.getId(),
                        organizationId,
                        "A-105",
                        "Compressor A-105",
                        NOW
                )
        );
    }

    @Test
    void rejectsInvalidInputWithoutSaving() {
        assertThatThrownBy(() ->
                service.create(UUID.randomUUID(), "A/105", "Compressor"))
                .isInstanceOf(IllegalArgumentException.class);

        verifyNoInteractions(repository);
    }

    @Test
    void rejectsMissingOrganizationWithoutSaving() {
        assertThatThrownBy(() ->
                service.create(null, "A-105", "Compressor"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Organization ID is required");

        verifyNoInteractions(repository);
    }
}