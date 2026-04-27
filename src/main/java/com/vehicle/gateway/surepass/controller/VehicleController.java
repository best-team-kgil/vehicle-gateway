package com.vehicle.gateway.surepass.controller;

import com.vehicle.gateway.surepass.dto.ChassisRequest;
import com.vehicle.gateway.surepass.dto.EngineRequest;
import com.vehicle.gateway.surepass.dto.RcRequest;
import com.vehicle.gateway.surepass.service.SurepassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/surepass/vehicle")
public class VehicleController {

    private final SurepassService surepassService;

    public VehicleController(SurepassService surepassService) {
        this.surepassService = surepassService;
    }

    @PostMapping("/chassis")
    public ResponseEntity<Object> lookupByChassis(@RequestBody ChassisRequest request) {
        log.info("Received request for chassis lookup: {}", request.getChassisNumber());
        try {
            Object result = surepassService.lookupByChassis(request.getChassisNumber());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to lookup by chassis: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/engine")
    public ResponseEntity<Object> lookupByEngine(@RequestBody EngineRequest request) {
        log.info("Received request for engine lookup: {}", request.getEngineNumber());
        try {
            Object result = surepassService.lookupByEngine(request.getEngineNumber());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to lookup by engine: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/rc")
    public ResponseEntity<Object> lookupByRc(@RequestBody RcRequest request) {
        log.info("Received request for RC lookup: {}", request.getRcNumber());
        try {
            Object result = surepassService.lookupByRc(request.getRcNumber());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Failed to lookup by RC: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
