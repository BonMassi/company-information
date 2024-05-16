package com.prueba.capital.humano.empresa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="EMPLOYEE_WORKED_HOURS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class EmployeeWorkedHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="EMPLOYEE_ID")
    private Employee employee;

    @Column(name="worked_hours")
    private Integer workedHours;

    @Column(name="worked_date")
    private Date workedDate;
}
