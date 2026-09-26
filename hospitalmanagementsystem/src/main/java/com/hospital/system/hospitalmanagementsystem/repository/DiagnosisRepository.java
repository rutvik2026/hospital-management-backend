package com.hospital.system.hospitalmanagementsystem.repository;

import com.hospital.system.hospitalmanagementsystem.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

}
