package com.hospital.system.hospitalmanagementsystem.repository;

import com.hospital.system.hospitalmanagementsystem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByUser_Id(long userId);
}
