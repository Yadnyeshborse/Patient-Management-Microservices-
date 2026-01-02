package com.microservice.pattern.Services;


import com.microservice.pattern.DTO.PatientRequestDTO;
import com.microservice.pattern.DTO.PatientResponseDTO;
import com.microservice.pattern.exception.EmailAlreadyExistException;
import com.microservice.pattern.mapper.PatientMapper;
import com.microservice.pattern.model.Patient;
import com.microservice.pattern.repositery.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServices {

    @Autowired
    private PatientRepository patientRepository;

    public List<PatientResponseDTO> findPatiendts(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientDTOs = patients.stream()
                .map(pat-> PatientMapper.toDTO(pat))
                .collect(Collectors.toList());
        return patientDTOs;
    }


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistException(t "Patient with email "+patientRequestDTO.getEmail()+" already exsists");
        }
        Patient patient=patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(patient);
    }




}
