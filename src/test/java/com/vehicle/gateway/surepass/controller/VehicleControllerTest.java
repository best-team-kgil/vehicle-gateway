package com.vehicle.gateway.surepass.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle.gateway.surepass.dto.ChassisRequest;
import com.vehicle.gateway.surepass.dto.EngineRequest;
import com.vehicle.gateway.surepass.dto.RcRequest;
import com.vehicle.gateway.surepass.service.SurepassService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurepassService surepassService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void lookupByChassis_Success() throws Exception {
        ChassisRequest request = new ChassisRequest();
        request.setChassisNumber("CH123");

        Map<String, String> expectedResponse = Map.of("status", "success", "data", "test data");

        when(surepassService.lookupByChassis("CH123")).thenReturn(expectedResponse);

        mockMvc.perform(post("/surepass/vehicle/chassis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("test data"));
    }

    @Test
    void lookupByChassis_ServiceException() throws Exception {
        ChassisRequest request = new ChassisRequest();
        request.setChassisNumber("CH123");

        when(surepassService.lookupByChassis(anyString())).thenThrow(new RuntimeException("Service Error"));

        mockMvc.perform(post("/surepass/vehicle/chassis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Service Error"));
    }

    @Test
    void lookupByEngine_Success() throws Exception {
        EngineRequest request = new EngineRequest();
        request.setEngineNumber("EN123");

        Map<String, String> expectedResponse = Map.of("status", "success", "data", "test data");

        when(surepassService.lookupByEngine("EN123")).thenReturn(expectedResponse);

        mockMvc.perform(post("/surepass/vehicle/engine")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("test data"));
    }

    @Test
    void lookupByEngine_ServiceException() throws Exception {
        EngineRequest request = new EngineRequest();
        request.setEngineNumber("EN123");

        when(surepassService.lookupByEngine(anyString())).thenThrow(new RuntimeException("Service Error"));

        mockMvc.perform(post("/surepass/vehicle/engine")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Service Error"));
    }

    @Test
    void lookupByRc_Success() throws Exception {
        RcRequest request = new RcRequest();
        request.setRcNumber("RC123");

        Map<String, String> expectedResponse = Map.of("status", "success", "data", "test data");

        when(surepassService.lookupByRc("RC123")).thenReturn(expectedResponse);

        mockMvc.perform(post("/surepass/vehicle/rc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("test data"));
    }

    @Test
    void lookupByRc_ServiceException() throws Exception {
        RcRequest request = new RcRequest();
        request.setRcNumber("RC123");

        when(surepassService.lookupByRc(anyString())).thenThrow(new RuntimeException("Service Error"));

        mockMvc.perform(post("/surepass/vehicle/rc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Service Error"));
    }
}
