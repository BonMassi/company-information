package com.prueba.capital.humano.empresa.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class EmployeeWorkedHoursDto {

    @Schema(
            description = "Status code in the response",example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message in the response",example = "Request processed successfully"
    )
    private String statusMsg;

    @Schema(
            description = "Total de horas trabajadas",example = "50 o 30"
    )
    private Integer total_worked_hours;
}
