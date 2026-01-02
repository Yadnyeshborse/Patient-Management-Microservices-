package com.microservice.pattern.mapper;

import com.microservice.pattern.DTO.PatientRequestDTO;
import com.microservice.pattern.DTO.PatientResponseDTO;
import com.microservice.pattern.model.Patient;

import java.time.LocalDateTime;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient) {
        // Implement the mapping logic from patient entity to PatientResponseDTO
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(patient.getId().toString());
        dto.setName(patient.getName());
        dto.setEmail(patient.getEmail());
        dto.setAddress(patient.getAddress());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());
        return dto;
    }

    //Implement
    public  static Patient toModel(PatientRequestDTO dto){
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setEmail(dto.getEmail());
        // Add parsing logic for dateOfBirth and registrationDate
        // For example, using LocalDateTime.parse() with appropriate formatter
        patient.setAddress(dto.getAddress());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setRegistrationDate(dto.getRegistrationDate());
        return patient;
    }

}
