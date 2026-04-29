package com.vehicle.gateway.service.impl;

import com.vehicle.gateway.model.VerificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurepassVerificationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SurepassVerificationService surepassService;

    private final String baseUrl = "https://sandbox.surepass.io/api/v1";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(surepassService, "baseUrl", baseUrl);
        ReflectionTestUtils.setField(surepassService, "surepassToken", "test-token");
    }

    @Test
    void verify_RcSuccess() {
        String idNumber = "RC123";
        Map<String, String> expectedResponse = Map.of("status", "success");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/full"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.verify(idNumber, VerificationType.RC);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void verify_ChassisSuccess() {
        String idNumber = "CH123";
        Map<String, String> expectedResponse = Map.of("status", "success");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/chassis"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.verify(idNumber, VerificationType.CHASSIS);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void verify_EngineSuccess() {
        String idNumber = "EN123";
        Map<String, String> expectedResponse = Map.of("status", "success");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/engine"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.verify(idNumber, VerificationType.ENGINE);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }
}
