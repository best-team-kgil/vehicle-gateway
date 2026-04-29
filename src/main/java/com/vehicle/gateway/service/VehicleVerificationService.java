package com.vehicle.gateway.service;

import com.vehicle.gateway.model.VerificationType;

public interface VehicleVerificationService {
    Object verify(String idNumber, VerificationType type);
    String getVendorName();
}
