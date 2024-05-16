package com.prueba.capital.humano.empresa.mappers;

import com.prueba.capital.humano.empresa.dto.EmployeeDto;
import com.prueba.capital.humano.empresa.entities.Employee;
import com.prueba.capital.humano.empresa.entities.Gender;
import com.prueba.capital.humano.empresa.entities.Job;

import java.util.ArrayList;
import java.util.List;

public class EmployeeMapper {

    public static EmployeeDto toEmployeeDTO(Employee employee) {
        EmployeeDto employeeDTO = new EmployeeDto();
        employeeDTO.setGenderId(employee.getGender().getId());
        employeeDTO.setJobId(employee.getJob().getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setBirthdate(employee.getBirthdate());


        if (employee.getJob() != null) {
            employeeDTO.setJob(JobMapper.toJobDto(employee.getJob()));
        }
        if (employee.getGender() != null) {
            employeeDTO.setGender(GenderMapper.toGenderDto(employee.getGender()));
        }
        return employeeDTO;
    }

    public static Employee toEmployee(EmployeeDto employeeDTO, Gender gender, Job job) {
        Employee employee = new Employee();
        employee.setGender(gender);
        employee.setJob(job);
        employee.setName(employeeDTO.getName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setBirthdate(employeeDTO.getBirthdate());
        return employee;
    }

    public static List<EmployeeDto> toEmployeeDTOList(List<Employee> employees) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        for (Employee employee : employees) {
            employeeDtoList.add(toEmployeeDTO(employee));
        }
        return employeeDtoList;
    }
}
