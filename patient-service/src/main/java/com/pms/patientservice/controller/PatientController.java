package com.pms.patientservice.controller;

import com.pms.patientservice.dto.PatientResponseDTO;
import com.pms.patientservice.service.PatientService;
import com.pms.patientservice.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<Object> getAllPatients(HttpServletRequest request){
        List<PatientResponseDTO> patients = patientService.getAllPatients();
        return ApiResponse.Send(request,"All patients details fetched successfully", HttpStatus.OK, patients);
//        return ResponseEntity.ok().body(patients);
    }
}
