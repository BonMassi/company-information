package com.prueba.capital.humano.empresa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmployeeUnderAgeException extends RuntimeException {

    public EmployeeUnderAgeException(String message){
        super(message);
    }
}
