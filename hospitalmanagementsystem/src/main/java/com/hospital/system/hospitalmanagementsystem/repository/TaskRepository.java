package com.hospital.system.hospitalmanagementsystem.repository;

import com.hospital.system.hospitalmanagementsystem.entity.Tasks;
import com.hospital.system.hospitalmanagementsystem.enums.TaskEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks,Long> {
    List<Tasks> findByUserId(long userId);

    long countByUserIdAndStatusNot(
            long userId,
            TaskEnum status
    );

    List<Tasks> findByAppointmentId(long appointmentId);
}
