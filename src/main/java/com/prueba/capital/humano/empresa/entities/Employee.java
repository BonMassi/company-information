package com.prueba.capital.humano.empresa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "EMPLOYEES")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="GENDER_ID")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name= "JOB_ID")
    private Job job;

    @Column(name="NAME")
    private String name;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="BIRTHDATE")
    private Date birthdate;


}
