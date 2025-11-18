package com.pms.patientservice.service;

import billing.BillingResponse;
import com.pms.patientservice.dto.PatientRequestDTO;
import com.pms.patientservice.dto.PatientResponseDTO;
import com.pms.patientservice.exception.EmailAlreadyExistException;
import com.pms.patientservice.exception.PatientNotFoundException;
import com.pms.patientservice.grpc.BillingServiceGrpcClient;
import com.pms.patientservice.kafka.KafkaProducer;
import com.pms.patientservice.mapper.PatientMapper;
import com.pms.patientservice.model.Patient;
import com.pms.patientservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public List<PatientResponseDTO> getAllPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        boolean isEmailExist = patientRepository.existsByEmail(patientRequestDTO.getEmail());

        if(isEmailExist){
            throw new EmailAlreadyExistException("A patient with this e-mail already exists " + patientRequestDTO.getEmail());
        }

        Patient patient = patientRepository.save(
                PatientMapper.toEntity(patientRequestDTO));

        BillingResponse billingAccount = billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
        kafkaProducer.sendEvent(patient, "PATIENT_CREATED");

        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID : "+ id));

        boolean isEmailExist = patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id);

        if(isEmailExist){
            throw new EmailAlreadyExistException("A patient with this e-mail already exists " + patientRequestDTO.getEmail());
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setDob(LocalDate.parse(patientRequestDTO.getDob(),formatter));
        patient.setAddress(patientRequestDTO.getAddress());

        Patient updatedPatient = patientRepository.save(patient);
        kafkaProducer.sendEvent(patient, "PATIENT_UPDATED");
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID : "+ id));

        patientRepository.delete(patient);
    }
}
