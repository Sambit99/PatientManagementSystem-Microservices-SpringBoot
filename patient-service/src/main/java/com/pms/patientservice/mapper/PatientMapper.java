package com.pms.patientservice.mapper;

import com.pms.patientservice.dto.PatientRequestDTO;
import com.pms.patientservice.dto.PatientResponseDTO;
import com.pms.patientservice.model.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public static Patient toEntity(PatientRequestDTO patientRequestDTO){
        Patient patient = new Patient();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDob(LocalDate.parse(patientRequestDTO.getDob(), formatter));
        patient.setRegisteredDate(LocalDate.parse(patientRequestDTO.getRegisteredDate(),formatter));

        return patient;
    }
}
