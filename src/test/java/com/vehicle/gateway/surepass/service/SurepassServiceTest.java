package com.vehicle.gateway.surepass.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurepassServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SurepassService surepassService;

    private final String baseUrl = "https://sandbox.surepass.io/api/v1";
    private final String surepassToken = "TEST_TOKEN";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(surepassService, "baseUrl", baseUrl);
        ReflectionTestUtils.setField(surepassService, "surepassToken", surepassToken);
    }

    @Test
    void lookupByChassis_Success() {
        String chassisNumber = "CH123";
        Map<String, String> expectedResponse = Map.of("status", "success", "data", "some data");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/chassis"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.lookupByChassis(chassisNumber);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void lookupByChassis_Exception() {
        String chassisNumber = "CH123";

        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(new RestClientException("API Error"));

        assertThrows(RestClientException.class, () -> surepassService.lookupByChassis(chassisNumber));
    }

    @Test
    void lookupByEngine_Success() {
        String engineNumber = "EN123";
        Map<String, String> expectedResponse = Map.of("status", "success", "data", "some data");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/engine"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.lookupByEngine(engineNumber);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void lookupByEngine_Exception() {
        String engineNumber = "EN123";

        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(new RestClientException("API Error"));

        assertThrows(RestClientException.class, () -> surepassService.lookupByEngine(engineNumber));
    }

    @Test
    void lookupByRc_Success() {
        String rcNumber = "RC123";
        Map<String, String> expectedResponse = Map.of("status", "success", "data", "some data");

        when(restTemplate.postForObject(eq(baseUrl + "/rc/full"), any(HttpEntity.class), eq(Object.class)))
                .thenReturn(expectedResponse);

        Object result = surepassService.lookupByRc(rcNumber);

        assertNotNull(result);
        assertEquals(expectedResponse, result);
    }

    @Test
    void lookupByRc_Exception() {
        String rcNumber = "RC123";

        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(Object.class)))
                .thenThrow(new RestClientException("API Error"));

        assertThrows(RestClientException.class, () -> surepassService.lookupByRc(rcNumber));
    }
}
