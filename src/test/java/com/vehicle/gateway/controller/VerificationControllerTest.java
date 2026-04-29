package com.vehicle.gateway.controller;

import com.vehicle.gateway.model.VerificationType;
import com.vehicle.gateway.service.VehicleVerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VerificationController.class)
public class VerificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleVerificationService verificationService;

    @Test
    void verify_RcSuccess() throws Exception {
        String rcNumber = "RC123";
        String vendor = "surepass";

        when(verificationService.getVendorName()).thenReturn(vendor);
        when(verificationService.verify(rcNumber, VerificationType.RC)).thenReturn(Map.of("data", "rc_success"));

        mockMvc.perform(get("/verification")
                .param("verificationType", vendor)
                .param("rcNumber", rcNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("rc_success"));
    }

    @Test
    void verify_ChassisSuccess() throws Exception {
        String chassisNumber = "CH123";
        String vendor = "surepass";

        when(verificationService.getVendorName()).thenReturn(vendor);
        when(verificationService.verify(chassisNumber, VerificationType.CHASSIS)).thenReturn(Map.of("data", "chassis_success"));

        mockMvc.perform(get("/verification")
                .param("verificationType", vendor)
                .param("chassisNumber", chassisNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("chassis_success"));
    }

    @Test
    void verify_EngineSuccess() throws Exception {
        String engineNumber = "EN123";
        String vendor = "surepass";

        when(verificationService.getVendorName()).thenReturn(vendor);
        when(verificationService.verify(engineNumber, VerificationType.ENGINE)).thenReturn(Map.of("data", "engine_success"));

        mockMvc.perform(get("/verification")
                .param("verificationType", vendor)
                .param("engineNumber", engineNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("engine_success"));
    }

    @Test
    void verify_MissingVendor() throws Exception {
        mockMvc.perform(get("/verification")
                .param("rcNumber", "123"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verify_MissingId() throws Exception {
        mockMvc.perform(get("/verification")
                .param("verificationType", "surepass"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void verify_UnsupportedVendor() throws Exception {
        when(verificationService.getVendorName()).thenReturn("surepass");

        mockMvc.perform(get("/verification")
                .param("verificationType", "unknown")
                .param("rcNumber", "123"))
                .andExpect(status().isBadRequest());
    }
}
