package com.prueba.capital.humano.empresa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Schema(
        name = "Employee",
        description = "Schema para tener la información del Employee"
)
@Data
public class EmployeeDto {

    @NotNull(message = "GenderId no puede ser nulo")
    @Schema(
            description = "GenderId"
    )
    private Long genderId;

    @NotNull(message = "JobId no puede ser nulo")
    @Schema(
            description = "JobId"
    )
    private Long jobId;

    @NotEmpty(message = "Name  no puede ser nulo o vacio")
    @Schema(
            description = "Name"
    )
    private String name;

    @NotEmpty(message = "LastName  no puede ser nulo o vacio")
    @Schema(
            description = "LastName"
    )
    private String lastName;


    @NotNull(message = "Birthdate no puede ser nulo")
    @Schema(
            description = "BirthDate"
    )
    private Date birthdate;


    private JobDto job;


    private GenderDto gender;
}
