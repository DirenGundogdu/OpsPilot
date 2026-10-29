package com.opspilot.operations.equipment.api;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opspilot.operations.equipment.application.EquipmentService;

@RestController 
@RequestMapping ("/api/v1/equipment")
@Profile ("local")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final UUID organizationId;

        public EquipmentController(
            EquipmentService service,
            @Value("${opspilot.local.organization-id}") UUID organizationId
    ) {
        this.equipmentService = service;
        this.organizationId = organizationId;
    }

    @PostMapping
    public ResponseEntity<EquipmentService.CreatedEquipment> create( @RequestBody CreateEquipmentRequest request ){ 

        var created = equipmentService.create(
                organizationId,
                request.code(),
                request.name()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


      public record CreateEquipmentRequest(String code, String name) {
    }
    

}
