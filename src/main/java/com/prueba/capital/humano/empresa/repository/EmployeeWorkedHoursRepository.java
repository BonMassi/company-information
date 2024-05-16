package com.prueba.capital.humano.empresa.repository;

import com.prueba.capital.humano.empresa.entities.EmployeeWorkedHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeWorkedHoursRepository extends JpaRepository<EmployeeWorkedHours,Long> {


    List<EmployeeWorkedHours> findAllByEmployeeId(Long employeeId);
}
