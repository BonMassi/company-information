package com.prueba.capital.humano.empresa.repository;

import com.prueba.capital.humano.empresa.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByName(String firstName);
    Optional<Employee> findByLastName(String lastName);

    List<Employee> findByJobId(Long jobId);
}
