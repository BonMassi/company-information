package com.prueba.capital.humano.empresa.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="GENDERS")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NAME")
    private String name;
}
