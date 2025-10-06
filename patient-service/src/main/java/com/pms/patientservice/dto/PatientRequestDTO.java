package com.pms.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientRequestDTO {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 25, message = "Name at-least be 3 Characters long & can't be more than 25 characters")
    private String name;

    @NotBlank(message = "DOB is required")
    private String dob;

    @NotBlank(message = "E-mail is required")
    @Email(message = "E-mail should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Registered date is required")
    private String registeredDate;
}
