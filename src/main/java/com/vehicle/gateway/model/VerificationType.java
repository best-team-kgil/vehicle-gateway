package com.vehicle.gateway.model;

public enum VerificationType {
    RC,
    CHASSIS,
    ENGINE;

    public static VerificationType fromString(String value) {
        for (VerificationType type : VerificationType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid verification type: " + value + ". Supported types are: RC, CHASSIS, ENGINE.");
    }
}
