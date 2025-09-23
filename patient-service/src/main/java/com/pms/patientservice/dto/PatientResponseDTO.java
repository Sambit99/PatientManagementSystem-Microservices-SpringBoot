package com.pms.patientservice.dto;

import lombok.Data;

@Data
public class PatientResponseDTO {
    private String id;
    private String name;
    private String dob;
    private String email;
    private String address;
}
