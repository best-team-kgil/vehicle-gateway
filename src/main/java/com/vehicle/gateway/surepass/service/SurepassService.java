package com.vehicle.gateway.surepass.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class SurepassService {

    private final RestTemplate restTemplate;

    @Value("${surepass.api.base-url}")
    private String baseUrl;

    @Value("${surepass.api.token}")
    private String surepassToken;

    public SurepassService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object lookupByChassis(String chassisNumber) {
        log.info("Initiating Surepass Chassis to RC lookup for chassis number: {}", chassisNumber);
        
        String url = baseUrl + "/rc/chassis";
        
        HttpHeaders headers = createHeaders();
        // Assuming the Surepass API takes the input as JSON like {"chassis_number": "..."}
        // Adjust the request payload structure according to actual Surepass API documentation
        Map<String, String> requestBody = Map.of("id_number", chassisNumber);
        
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Calling Surepass API URL: {}", url);
            Object response = restTemplate.postForObject(url, requestEntity, Object.class);
            log.info("Successfully received response from Surepass Chassis to RC API");
            log.debug("Response payload: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error occurred while calling Surepass Chassis to RC API: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Object lookupByEngine(String engineNumber) {
        log.info("Initiating Surepass Engine to RC lookup for engine number: {}", engineNumber);
        
        String url = baseUrl + "/rc/engine";
        
        HttpHeaders headers = createHeaders();
        // Assuming the Surepass API takes the input as JSON like {"engine_number": "..."}
        // Adjust the request payload structure according to actual Surepass API documentation
        Map<String, String> requestBody = Map.of("id_number", engineNumber);
        
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Calling Surepass API URL: {}", url);
            Object response = restTemplate.postForObject(url, requestEntity, Object.class);
            log.info("Successfully received response from Surepass Engine to RC API");
            log.debug("Response payload: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error occurred while calling Surepass Engine to RC API: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Object lookupByRc(String rcNumber) {
        log.info("Initiating Surepass RC Full lookup for RC number: {}", rcNumber);
        
        String url = baseUrl + "/rc/full";
        
        HttpHeaders headers = createHeaders();
        Map<String, String> requestBody = Map.of("id_number", rcNumber);
        
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("Calling Surepass API URL: {}", url);
            Object response = restTemplate.postForObject(url, requestEntity, Object.class);
            log.info("Successfully received response from Surepass RC Full API");
            log.debug("Response payload: {}", response);
            return response;
        } catch (Exception e) {
            log.error("Error occurred while calling Surepass RC Full API: {}", e.getMessage(), e);
            throw e;
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(surepassToken);
        // Note: Some APIs use headers like 'Authorization: Bearer <token>' 
        // while others might use custom headers like 'x-api-key'.
        // Assuming Bearer Auth here, but it should be adjusted based on Surepass docs.
        return headers;
    }
}
