package com.pms.patientservice.mapper;

import com.pms.patientservice.dto.PatientResponseDTO;
import com.pms.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient){
        PatientResponseDTO patientDTO = new PatientResponseDTO();

        patientDTO.setId(String.valueOf(patient.getId()));
        patientDTO.setName(patient.getName());
        patientDTO.setDob(String.valueOf(patient.getDob()));
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setAddress(patient.getAddress());

        return patientDTO;
    }
}
