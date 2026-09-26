package com.hospital.system.hospitalmanagementsystem.service;

import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineRequest;
import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisResponseDto;

import java.util.List;

public interface DiagnosisService {
    public DiagnosisResponseDto addDiagnosis(DiagnosisRequestDto diagnosisRequestDto,long appointId);

    public AddMedicineResponseDto addMedicine(AddMedicineRequest addMedicineRequest,long diaId);
    public DiagnosisResponseDto updateDiagnosis(DiagnosisRequestDto diagnosisRequestDto,long diaId);
    public AddMedicineResponseDto updateMedicine(AddMedicineRequest addMedicineRequest,long id);
    public DiagnosisResponseDto getDiagnosis(long appointmentId);
    public List<AddMedicineResponseDto > getMedicines(long diaId);

}
