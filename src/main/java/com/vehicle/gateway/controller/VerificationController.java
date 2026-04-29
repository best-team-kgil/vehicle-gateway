package com.vehicle.gateway.controller;

import com.vehicle.gateway.model.VerificationType;
import com.vehicle.gateway.service.VehicleVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class VerificationController {

    private final Map<String, VehicleVerificationService> verificationServices;

    public VerificationController(List<VehicleVerificationService> services) {
        this.verificationServices = services.stream()
                .collect(Collectors.toMap(VehicleVerificationService::getVendorName, s -> s));
    }

    @GetMapping("/verification")
    public ResponseEntity<Object> verify(
            @RequestParam String verificationType, // verificationType is the vendor
            @RequestParam(required = false) String rcNumber,
            @RequestParam(required = false) String chassisNumber,
            @RequestParam(required = false) String engineNumber) {

        log.info("Received verification request for vendor: {}. Params: rc={}, chassis={}, engine={}", 
                verificationType, rcNumber, chassisNumber, engineNumber);

        VehicleVerificationService service = verificationServices.get(verificationType.toLowerCase());
        if (service == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unsupported vendor: " + verificationType));
        }

        String idNumber;
        VerificationType type;

        if (rcNumber != null && !rcNumber.isEmpty()) {
            idNumber = rcNumber;
            type = VerificationType.RC;
        } else if (chassisNumber != null && !chassisNumber.isEmpty()) {
            idNumber = chassisNumber;
            type = VerificationType.CHASSIS;
        } else if (engineNumber != null && !engineNumber.isEmpty()) {
            idNumber = engineNumber;
            type = VerificationType.ENGINE;
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "At least one of rcNumber, chassisNumber, or engineNumber must be provided."));
        }

        try {
            Object result = service.verify(idNumber, type);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Verification failed for vendor {}: {}", verificationType, e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
