package com.prueba.capital.humano.empresa.dto;

import lombok.Data;

import java.util.Date;

@Data
public class WorkedHoursRequestDto {
    private Long employeeId;
    private Date startDate;
    private Date endDate;
}
