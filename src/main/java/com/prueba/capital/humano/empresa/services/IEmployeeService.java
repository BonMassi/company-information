package com.prueba.capital.humano.empresa.services;

import com.prueba.capital.humano.empresa.dto.EmployeeDto;
import com.prueba.capital.humano.empresa.dto.EmployeeWorkedHoursDto;
import com.prueba.capital.humano.empresa.dto.WorkedHoursRequestDto;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IEmployeeService {

    void createEmployee(EmployeeDto employeeDto);

    List<EmployeeDto> findEmployees(Long jobId);

    CompletableFuture<EmployeeDto> findEmployees(String lastName);


    Integer findWorkedHours(WorkedHoursRequestDto request);

    Double findPayment(WorkedHoursRequestDto request);
}
