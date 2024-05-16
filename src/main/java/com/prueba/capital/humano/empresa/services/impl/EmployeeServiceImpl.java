package com.prueba.capital.humano.empresa.services.impl;

import ch.qos.logback.core.joran.spi.JoranException;
import com.prueba.capital.humano.empresa.dto.EmployeeDto;
import com.prueba.capital.humano.empresa.dto.EmployeeWorkedHoursDto;
import com.prueba.capital.humano.empresa.dto.WorkedHoursRequestDto;
import com.prueba.capital.humano.empresa.entities.Employee;
import com.prueba.capital.humano.empresa.entities.EmployeeWorkedHours;
import com.prueba.capital.humano.empresa.entities.Gender;
import com.prueba.capital.humano.empresa.entities.Job;
import com.prueba.capital.humano.empresa.exception.*;
import com.prueba.capital.humano.empresa.mappers.EmployeeMapper;
import com.prueba.capital.humano.empresa.repository.EmployeeRepository;
import com.prueba.capital.humano.empresa.repository.EmployeeWorkedHoursRepository;
import com.prueba.capital.humano.empresa.repository.GenderRepository;
import com.prueba.capital.humano.empresa.repository.JobRepository;
import com.prueba.capital.humano.empresa.services.IEmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {

    private EmployeeRepository employeeRepository;
    private GenderRepository genderRepository;
    private JobRepository jobRepository;
    private EmployeeWorkedHoursRepository employeeWorkedHoursRepository;

    @Override
    public void createEmployee(EmployeeDto employeeDto) {


        Gender gender = genderRepository.findById(employeeDto.getGenderId()).orElse(null);

        Job job = jobRepository.findById(employeeDto.getJobId()).orElse(null);

        Employee employee = EmployeeMapper.toEmployee(employeeDto,gender,job);

        Optional<Employee> optionalEmployeeName = employeeRepository.findByName(employee.getName());
        Optional<Employee> optionalEmployeeLast = employeeRepository.findByLastName(employee.getLastName());

        if(optionalEmployeeName.isPresent()){
            throw  new EmployeeAlreadyExistsException("Employee ya existe con ese Name: "+ employee.getName());
        }else if(optionalEmployeeLast.isPresent()){
            throw  new EmployeeAlreadyExistsException("Employee ya existe con ese LastName: "+ employee.getLastName());
        }else if(!checaEdad(employee.getBirthdate())){
            throw  new EmployeeUnderAgeException("Employee no tiene la mayoria de edad: "+ employee.getBirthdate().toString());
        }

        employeeRepository.save(employee);

    }

    @Override
    public List<EmployeeDto> findEmployees(Long jobId) {

        //List<Employee> employees = employeeRepository.findByJobId(jobId); //ES mejor traerselo asi en vez de filtarlo por emprresion lambda

        List<Employee> employees = employeeRepository.findAll();

        Job job = jobRepository.findById(jobId).orElse(null);

        if(job == null){
          throw new JobNoExistsException("Job id no se encunetra registrado " + jobId);
        }
        List<Employee> empleadosFiltrados = new java.util.ArrayList<>(employees.stream()
                .filter(employee -> employee.getJob().equals(job))
                .toList());

        empleadosFiltrados.sort(Comparator.comparing(Employee::getLastName));

        Map<String, Long> empleadoPorLastName = empleadosFiltrados.stream()
                .collect(Collectors.groupingBy(Employee::getLastName, Collectors.counting()));


        List<Employee> employeesSorteadoPorLastName = empleadoPorLastName.entrySet().stream()
                .flatMap(entry -> empleadosFiltrados.stream().filter(employee -> employee.getLastName().equals(entry.getKey())))
                .toList();



        return   EmployeeMapper.toEmployeeDTOList(employeesSorteadoPorLastName);
    }



    @Override
    @Async("asyncExecutor")
    public CompletableFuture<EmployeeDto> findEmployees(String lastName) {


        Optional<Employee> optionalEmployee = employeeRepository.findByLastName(lastName);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            // Mapea el empleado encontrado a EmployeeDto
            EmployeeDto employeeDtoResult = EmployeeMapper.toEmployeeDTO(employee);

            // Simplemente duerme el hilo por 1 segundo para simular un procesamiento
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return CompletableFuture.completedFuture(employeeDtoResult);
        } else {
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    public Integer findWorkedHours(WorkedHoursRequestDto request) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(request.getEmployeeId());

        if(optionalEmployee.isEmpty()){
            throw  new ResourceNotFoundException("Employee","employeeId",request.getEmployeeId().toString());
        }

        List<EmployeeWorkedHours> workedHoursList = employeeWorkedHoursRepository.findAllByEmployeeId(request.getEmployeeId());

       if(workedHoursList.isEmpty()){
           throw  new WorkedHoursException("El empleado  no tiene horas trabajadas" + request.getEmployeeId());
       }else if(request.getStartDate().after(request.getEndDate())){
           throw  new DateUnderLimitsException("La fecha de inicio debe ser menor que la fecha de fin");
       }


        int totalWorkedHours = 0;
        for (EmployeeWorkedHours workedHours : workedHoursList) {
            Date workedDate = workedHours.getWorkedDate();
            if (workedDate != null && !workedDate.before(request.getStartDate()) && !workedDate.after(request.getEndDate())) {
                totalWorkedHours += workedHours.getWorkedHours();
            }
        }
        return totalWorkedHours;

    }

    @Override
    public Double findPayment(WorkedHoursRequestDto request) {

        Optional<Employee> optionalEmployee = employeeRepository.findById(request.getEmployeeId());

        if(optionalEmployee.isEmpty()){
            throw  new ResourceNotFoundException("Employee","employeeId",request.getEmployeeId().toString());
        }


        Optional<Job> optionalJob = jobRepository.findById(optionalEmployee.get().getJob().getId());

        if(optionalJob.isEmpty()){
            throw  new ResourceNotFoundException("Job","jobId",request.getEmployeeId().toString());
        }

        Integer totalHoras = findWorkedHours(request);

        return totalHoras*optionalJob.get().getSalary();
    }


    private Boolean checaEdad(Date birthdate){
        LocalDate fechaNacimiento = birthdate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate fecheActual = LocalDate.now();
        int edad = Period.between(fechaNacimiento,fecheActual).getYears();

        return edad >= 18;
    }
}
