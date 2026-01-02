package com.microservice.pattern.Controller;

import com.microservice.pattern.DTO.PatientRequestDTO;
import com.microservice.pattern.DTO.PatientResponseDTO;
import com.microservice.pattern.Services.PatientServices;
import com.microservice.pattern.common.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")  //http://localhost:8082/patients
public class PatientController {

    private final PatientServices patientServices;

    public PatientController(PatientServices patientServices) {
        this.patientServices = patientServices;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllPatients() {
        List<PatientResponseDTO> patients = patientServices.findPatiendts();

        if (patients.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("No patients found", 404));
        }

        return ResponseEntity.ok(
                ApiResponse.success("Patients fetched successfully", patients, patients.size())
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createPatient(
            @Valid @RequestBody PatientRequestDTO patientRequestDTO) {

        PatientResponseDTO createdPatient = patientServices.createPatient(patientRequestDTO);

        return ResponseEntity.status(201)
                .body(ApiResponse.success("Patient created successfully", createdPatient, 1));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updatePatient(
            @PathVariable UUID id, @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO updatedPatient = patientServices.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Patient updated successfully", updatedPatient, 1)
        );
    }


}
