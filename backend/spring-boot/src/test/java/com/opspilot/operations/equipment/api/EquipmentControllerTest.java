package com.opspilot.operations.equipment.api;

import com.opspilot.operations.equipment.application.EquipmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = EquipmentController.class,
        properties = "opspilot.local.organization-id="
                + "00000000-0000-0000-0000-000000000001"
)
@ActiveProfiles("local")
class EquipmentControllerTest {

    private static final UUID ORGANIZATION_ID =
            UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EquipmentService service;

    @Test
    void createsEquipmentUsingConfiguredOrganization() throws Exception {
        UUID equipmentId = UUID.randomUUID();

        var created = new EquipmentService.CreatedEquipment(
                equipmentId,
                ORGANIZATION_ID,
                "A-105",
                "Compressor A-105",
                Instant.parse("2026-10-29T10:00:00Z")
        );

        given(service.create(
                ORGANIZATION_ID,
                "A-105",
                "Compressor A-105"
        )).willReturn(created);

        mockMvc.perform(post("/api/v1/equipment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "code": "A-105",
                                  "name": "Compressor A-105"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(equipmentId.toString()))
                .andExpect(jsonPath("$.organizationId")
                        .value(ORGANIZATION_ID.toString()))
                .andExpect(jsonPath("$.code").value("A-105"))
                .andExpect(jsonPath("$.name").value("Compressor A-105"));

        verify(service).create(
                ORGANIZATION_ID,
                "A-105",
                "Compressor A-105"
        );
    }
}