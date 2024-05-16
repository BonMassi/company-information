package com.prueba.capital.humano.empresa.controllers;

import com.prueba.capital.humano.empresa.constants.CompanyConstants;
import com.prueba.capital.humano.empresa.dto.*;
import com.prueba.capital.humano.empresa.entities.EmployeeWorkedHours;
import com.prueba.capital.humano.empresa.services.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Tag(
        name = "CRUD REST APIs para prueba de Capital Humano",
        description = "CRUD REST APIs de prueba de Capital Humano para  CREATE,UPDATE,FETCH AND DELETE en la compañia"
)
@RestController
@RequestMapping(path = "/api",produces ={MediaType.APPLICATION_JSON_VALUE} )
@Validated
public class CompanyController {

    private final IEmployeeService iEmployeeService;

    public CompanyController(IEmployeeService iEmployeeService){
        this.iEmployeeService =iEmployeeService;
    }

    @Operation(
            summary = "Create Employee REST API",
            description = "REST API para crear un nuevo Employee"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        iEmployeeService.createEmployee(employeeDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CompanyConstants.STATUS_201,CompanyConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Get Employees REST API",
            description = "REST API para obtener los empleados de la empresa"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }

    )
    @GetMapping("/findemployees/{jobId}")
    public List<EmployeeDto> getEmployees(@PathVariable(name="jobId") Long jobId ){
        return iEmployeeService.findEmployees(jobId);
    }


    @PostMapping("/findMultipleEmployees")
    public List<EmployeeDto> processEmployees(@RequestBody List<EmployeeDto> employees){

        List<EmployeeDto> resultList = new ArrayList<>();

        //
        List<CompletableFuture<EmployeeDto>> futures = new ArrayList<>();


        for (EmployeeDto employeeDto : employees) {
            CompletableFuture<EmployeeDto> futureResult = iEmployeeService.findEmployees(employeeDto.getLastName());
            futures.add(futureResult);
        }


        for (CompletableFuture<EmployeeDto> future : futures) {
            try {
               EmployeeDto employeeDto = future.get();
                if (employeeDto != null) {
                    resultList.add(employeeDto);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return resultList;
    }


    @Operation(
            summary = "Get Worked Hours REST API",
            description = "REST API para obtener las horas trabajadas por empleado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }

    )
    @PostMapping("/findWorkedHours")
    public ResponseEntity<EmployeeWorkedHoursDto> findWorkedHours(@RequestBody WorkedHoursRequestDto request){

        Integer totalHoras = iEmployeeService.findWorkedHours(request);

        EmployeeWorkedHoursDto responseDto = new EmployeeWorkedHoursDto("200", "Request processed successfully", totalHoras);

        return ResponseEntity.ok(responseDto);


    }

    @PostMapping("/findPayment")
    public ResponseEntity<PaymentDto> findPayment(@RequestBody WorkedHoursRequestDto request){

        Double  payment = iEmployeeService.findPayment(request);

        PaymentDto responseDto = new PaymentDto("200","Request processed successfully", payment);

        return ResponseEntity.ok(responseDto);
    }




}
