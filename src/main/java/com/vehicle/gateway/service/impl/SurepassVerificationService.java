package com.vehicle.gateway.service.impl;

import com.vehicle.gateway.model.VerificationType;
import com.vehicle.gateway.service.VehicleVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service("surepass")
public class SurepassVerificationService implements VehicleVerificationService {

    private final RestTemplate restTemplate;

    @Value("${surepass.api.base-url}")
    private String baseUrl;

    @Value("${surepass.api.token}")
    private String surepassToken;

    public SurepassVerificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Object verify(String idNumber, VerificationType type) {
        log.info("Initiating Surepass {} verification for: {}", type, idNumber);
        
        String endpoint;
        switch (type) {
            case RC:
                endpoint = "/rc/full";
                break;
            case CHASSIS:
                endpoint = "/rc/chassis";
                break;
            case ENGINE:
                endpoint = "/rc/engine";
                break;
            default:
                throw new IllegalArgumentException("Unsupported verification type: " + type);
        }

        String url = baseUrl + endpoint;
        HttpHeaders headers = createHeaders();
        Map<String, String> requestBody = Map.of("id_number", idNumber);
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Calling Surepass API URL: {}", url);
            Object response = restTemplate.postForObject(url, requestEntity, Object.class);
            log.info("Successfully received response from Surepass {} API", type);
            return response;
        } catch (Exception e) {
            log.error("Error occurred while calling Surepass {} API: {}", type, e.getMessage());
            throw e;
        }
    }

    @Override
    public String getVendorName() {
        return "surepass";
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(surepassToken);
        return headers;
    }
}
