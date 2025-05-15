package com.maveric.crm.exceptionhandlers;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerDetailsNotFoundException.class)
    public ResponseEntity<String> handleCustomerDetailsNotFoundException(CustomerDetailsNotFoundException  e){
        ResponseEntity<String> responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return responseEntity;
    }
}
