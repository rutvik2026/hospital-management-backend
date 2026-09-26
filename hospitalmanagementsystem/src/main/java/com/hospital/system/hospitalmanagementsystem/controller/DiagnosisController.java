package com.hospital.system.hospitalmanagementsystem.controller;

import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineRequest;
import com.hospital.system.hospitalmanagementsystem.dto.AddMedicineResponseDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisRequestDto;
import com.hospital.system.hospitalmanagementsystem.dto.DiagnosisResponseDto;
import com.hospital.system.hospitalmanagementsystem.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    @PostMapping("/doctor/add/diagnosis/appoint/{appointId}")
    public ResponseEntity<DiagnosisResponseDto> addDiagnosis(@RequestBody DiagnosisRequestDto diagnosisRequestDto,@PathVariable long appointId){
        System.out.println("appointid "+appointId);
        DiagnosisResponseDto response=diagnosisService.addDiagnosis(diagnosisRequestDto,appointId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/doctor/add/medicine/{diaId}")
    public ResponseEntity<AddMedicineResponseDto> addMedicine(@RequestBody AddMedicineRequest addMedicineRequest,@PathVariable long diaId){
        AddMedicineResponseDto response=diagnosisService.addMedicine(addMedicineRequest,diaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/doctor/update/diagnosis/{diaId}")
    public ResponseEntity<DiagnosisResponseDto> updateDiagnosis(@RequestBody DiagnosisRequestDto diagnosisRequestDto,@PathVariable long diaId){
        DiagnosisResponseDto response=diagnosisService.updateDiagnosis(diagnosisRequestDto,diaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PatchMapping("/doctor/update/medicine/{id}")
    public ResponseEntity<AddMedicineResponseDto> updateMedicine(@RequestBody AddMedicineRequest addMedicineRequest,@PathVariable long id){
        AddMedicineResponseDto response=diagnosisService.updateMedicine(addMedicineRequest,id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/any/get/dia/{appointmentId}")
    public ResponseEntity<DiagnosisResponseDto> getDiagnosis( @PathVariable("appointmentId") long appointmentId){
        DiagnosisResponseDto response=diagnosisService.getDiagnosis(appointmentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/any/get/med/{diaId}")
    public ResponseEntity<List<AddMedicineResponseDto>> getMedicines( @PathVariable("diaId") long diaId){
        List<AddMedicineResponseDto> response=diagnosisService.getMedicines(diaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
