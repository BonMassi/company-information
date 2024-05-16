package com.prueba.capital.humano.empresa.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="JOBS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="salary")
    private Double salary;
}
