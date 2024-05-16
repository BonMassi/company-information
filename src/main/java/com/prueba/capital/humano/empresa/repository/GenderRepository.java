package com.prueba.capital.humano.empresa.repository;

import com.prueba.capital.humano.empresa.entities.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GenderRepository extends JpaRepository<Gender,Long> {

}
