package com.prueba.capital.humano.empresa.repository;

import com.prueba.capital.humano.empresa.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
