package com.pms.patientservice.controller;

import com.pms.patientservice.dto.PatientRequestDTO;
import com.pms.patientservice.dto.PatientResponseDTO;
import com.pms.patientservice.dto.validators.CreatePatientValidationGroup;
import com.pms.patientservice.service.PatientService;
import com.pms.patientservice.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API for managing Patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<Object> getAllPatients(HttpServletRequest request){
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ApiResponse.Send(request,"All patients details fetched successfully", HttpStatus.OK, patients);
//        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new patient")
    public ResponseEntity<Object> createNewPatient(
            HttpServletRequest request,
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO patientRequestDTO){
        PatientResponseDTO newPatient = patientService.createPatient(patientRequestDTO);
        return ApiResponse.Send(request,"New patient created successfully", HttpStatus.CREATED, newPatient);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a patient")
    public ResponseEntity<Object> updatePatient(HttpServletRequest request, @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO, @PathVariable UUID id){
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id,patientRequestDTO);
        return ApiResponse.Send(request,"Patient updated successfully", HttpStatus.OK, patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<Object> deletePatient(HttpServletRequest request, @PathVariable UUID id){
        patientService.deletePatient(id);
        return ApiResponse.Send(request,"Patient record deleted successfully",HttpStatus.NO_CONTENT,null);
    }
}
