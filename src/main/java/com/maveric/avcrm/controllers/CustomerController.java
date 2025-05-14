package com.maveric.avcrm.controllers;

import com.maveric.avcrm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.avcrm.pojos.Customer;
import com.maveric.avcrm.services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerServices customerServices;


    //Create Customer
    @PostMapping(value = "/v1/customer" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> acceptCustomerDetails(@RequestBody Customer customerToBeInsert){
       return  new ResponseEntity<>(customerServices.acceptCustomerDetails(customerToBeInsert), HttpStatus.CREATED);
    }

    // Delete Customer By Id
    @GetMapping( value="/v1/customer/delete/{id}" )
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable int id ) throws CustomerDetailsNotFoundException {
        customerServices.removeCustomerDetailsById(id);
        return new ResponseEntity<>("Customer Successfully Deleted.", HttpStatus.OK);
        //return  new ResponseEntity<>(employeeServices.getEmployeeDetails(id),HttpStatus.OK);
    }

    // Get Customer by id
    @GetMapping( value="/v1/customer/id/{id}" )
    public ResponseEntity<Customer> getCustomerDetailsById(@PathVariable int id) throws CustomerDetailsNotFoundException {
        Customer customer = customerServices.getCustomerDetailsById(id);
        return new ResponseEntity<>(customer ,HttpStatus.OK);
    }

    //Get All Customer By Age
    @GetMapping( value="/v1/customer/age/{age}" )
    public ResponseEntity <List<Customer>> getCustomerDetailsByAge(@PathVariable int age) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByAge(age);
        return new ResponseEntity<>(customersToBeFound,HttpStatus.OK);
    }

    //Get All Customers by gender
    @GetMapping( value="/v1/customer/gender/{gender}" )
    public ResponseEntity <List<Customer>> getCustomerDetailsByGender(@PathVariable String gender) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByGender(gender);
        return new ResponseEntity<>(customersToBeFound,HttpStatus.OK);
    }

}