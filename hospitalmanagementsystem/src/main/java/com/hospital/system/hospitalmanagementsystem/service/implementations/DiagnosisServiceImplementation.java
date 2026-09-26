package com.hospital.system.hospitalmanagementsystem.service.implementations;

import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineRequest;
import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisResponseDto;
import com.hospital.system.hospitalmanagementsystem.entity.Appointment;
import com.hospital.system.hospitalmanagementsystem.entity.Diagnosis;
import com.hospital.system.hospitalmanagementsystem.entity.PrescriptionMedicines;
import com.hospital.system.hospitalmanagementsystem.repository.AppointmentRepository;
import com.hospital.system.hospitalmanagementsystem.repository.DiagnosisRepository;
import com.hospital.system.hospitalmanagementsystem.repository.PrescriptionMedicinesRepository;
import com.hospital.system.hospitalmanagementsystem.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImplementation implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final AppointmentRepository appointmentRepository;

    private final PrescriptionMedicinesRepository prescriptionMedicinesRepository;
    @Override
    @Transactional
    public DiagnosisResponseDto addDiagnosis(DiagnosisRequestDto diagnosisRequestDto,long appointId){
        System.out.println("appId"+appointId);
        Appointment appointment=appointmentRepository.findById(appointId)
                .orElseThrow(()->new RuntimeException("invalide appointId"));

        Diagnosis newDiagnosis=Diagnosis.builder().prescription(diagnosisRequestDto.getPrescription())
                .discription(diagnosisRequestDto.getDiscription()).appointment(appointment)
                .build();

        Diagnosis diagnosis=diagnosisRepository.save(newDiagnosis);

        appointment.setDiagnosis(diagnosis);

        return DiagnosisResponseDto.builder()
                .appointment(diagnosis.getAppointment()).discription(diagnosis.getDiscription())
                .id(diagnosis.getId()).build();
    }

    @Override
    @Transactional
    public AddMedicineResponseDto addMedicine(AddMedicineRequest addMedicineRequest,long diaId){
        Diagnosis diagnosis=diagnosisRepository.findById(diaId)
                .orElseThrow(()->new RuntimeException("Invalide diaId"));
        PrescriptionMedicines prescriptionMedicines= PrescriptionMedicines.builder()
                .medicineName(addMedicineRequest.getMedicineName())
                .dosage(addMedicineRequest.getDosage()).frequency(addMedicineRequest.getFrequency())
                .diagnosis(diagnosis)
                .instructions(addMedicineRequest.getInstructions()).durationDays(addMedicineRequest.getDurationDays())
                .build();

        PrescriptionMedicines newPrescriptioMedicine=prescriptionMedicinesRepository.save(prescriptionMedicines);



        diagnosis.addMedicines(newPrescriptioMedicine);



        return AddMedicineResponseDto.builder()
                .diagnosisId(newPrescriptioMedicine.getDiagnosis().getId()).medicineName(newPrescriptioMedicine.getMedicineName())
                .instructions(newPrescriptioMedicine.getInstructions()).id(newPrescriptioMedicine.getId()).build();
    }


    @Override
    public DiagnosisResponseDto updateDiagnosis(DiagnosisRequestDto diagnosisRequestDto,long diaId){
        Diagnosis diagnosis=diagnosisRepository.findById(diaId)
                .orElseThrow(()->new RuntimeException("invalide diagnosis id"));
        if(diagnosisRequestDto.getAppointment()!=null){
            diagnosis.setAppointment(diagnosisRequestDto.getAppointment());
        }
        if(diagnosisRequestDto.getMedicines()!=null){
            diagnosis.setMedicines(diagnosisRequestDto.getMedicines());
        }
        if(diagnosisRequestDto.getDiscription()!=null){
            diagnosis.setDiscription(diagnosisRequestDto.getDiscription());
        }
        if(diagnosisRequestDto.getPrescription()!=null){
            diagnosis.setPrescription(diagnosisRequestDto.getPrescription());
        }
        Diagnosis updatedDia=diagnosisRepository.save(diagnosis);
        return DiagnosisResponseDto.builder()
                .id(updatedDia.getId()).appointment(updatedDia.getAppointment()).discription(updatedDia.getDiscription())
                .build();
    }
    @Override
    public AddMedicineResponseDto updateMedicine(AddMedicineRequest addMedicineRequest,long id){
        PrescriptionMedicines prescriptionMedicines=prescriptionMedicinesRepository.findById(id)
                .orElseThrow(()->new RuntimeException("invalide medicine id"));

        if(addMedicineRequest.getMedicineName()!=null){
            prescriptionMedicines.setMedicineName(addMedicineRequest.getMedicineName());
        }

        if(addMedicineRequest.getDiagnosis()!=null){
            prescriptionMedicines.setDiagnosis(addMedicineRequest.getDiagnosis());
        }

        if(addMedicineRequest.getInstructions()!=null){
            prescriptionMedicines.setInstructions(addMedicineRequest.getInstructions());
        }

        if(addMedicineRequest.getFrequency()!=null){
            prescriptionMedicines.setFrequency(addMedicineRequest.getFrequency());
        }

        if(addMedicineRequest.getDosage()!=null){
            prescriptionMedicines.setDosage(addMedicineRequest.getDosage());
        }
        Integer durationDays=addMedicineRequest.getDurationDays();
        if(durationDays!=null){
            prescriptionMedicines.setMedicineName(addMedicineRequest.getMedicineName());
        }

        PrescriptionMedicines updatedPM=prescriptionMedicinesRepository.save(prescriptionMedicines);
        return AddMedicineResponseDto.builder()
                .medicineName(updatedPM.getMedicineName()).instructions(updatedPM.getInstructions())
                .diagnosisId(updatedPM.getDiagnosis().getId()).build();
    }
    @Override
    public DiagnosisResponseDto getDiagnosis(long appointmentId){
        Appointment appointment=appointmentRepository.findById(appointmentId)
                .orElseThrow(()->new RuntimeException("Invalide appoint id"));

        Diagnosis diagnosis=appointment.getDiagnosis();
        if (diagnosis == null) {
        return null;
    }
        return DiagnosisResponseDto.builder()
                .id(diagnosis.getId())
                .discription(diagnosis.getDiscription()).appointmentId(appointmentId)
                .prescription(diagnosis.getPrescription()).build();
    }

    @Override
    public List<AddMedicineResponseDto > getMedicines(long diaId){
        Diagnosis diagnosis=diagnosisRepository.findById(diaId)
                .orElseThrow(()->new RuntimeException("Invalide dia id"));

        List<PrescriptionMedicines> medicines=diagnosis.getMedicines();

        List<AddMedicineResponseDto> addMedicineResponseDtos=new ArrayList<>();

        for(PrescriptionMedicines med:medicines){
            AddMedicineResponseDto addMedicineResponseDto=AddMedicineResponseDto.builder().id(med.getId())
                    .medicineName(med.getMedicineName())
                    .instructions(med.getInstructions()).diagnosisId(med.getDiagnosis().getId())
                    .dosage(med.getDosage()).frequency(med.getFrequency()).durationDays(med.getDurationDays()).build();
            addMedicineResponseDtos.add(addMedicineResponseDto);
        }

        return addMedicineResponseDtos;

    }
}
