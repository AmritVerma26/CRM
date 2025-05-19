package com.maveric.crm.controllers;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.services.CustomerServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerServices customerServices;

    //Create Customer
    @PostMapping(value = "/v1/customer" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> acceptCustomerDetails(@Valid @RequestBody Customer customerToBeInsert){
       return  new ResponseEntity<>(customerServices.acceptCustomerDetails(customerToBeInsert), HttpStatus.CREATED);
    }

    // Delete Customer By Id
    @GetMapping( value="/v1/customer/delete/{id}" )
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable int id ) throws CustomerDetailsNotFoundException {
        customerServices.removeCustomerDetailsById(id);
        return new ResponseEntity<>("Customer Successfully Deleted.", HttpStatus.OK);
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

    //Get All Customers
    @GetMapping(value = "/v1/customer/all")
    public ResponseEntity <List<Customer>> getAllCustomers() throws CustomerDetailsNotFoundException {
        List<Customer> allCustomers = customerServices.getAllCustomers();
        return new ResponseEntity<>(allCustomers, HttpStatus.OK);
    }

    //Update Customer
    @PutMapping("/v1/customer/update/{customer}")
    public ResponseEntity<String> updateCustomerDetails(@Valid @RequestBody Customer customer) throws CustomerDetailsNotFoundException {
        customerServices.updateCustomerDetails(customer);
        return new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
    }

    //Find By First Name
    @GetMapping(value = "/v1/customer/firstName/{firstName}")
    public ResponseEntity <List<Customer>> getCustomerByFirstName(@PathVariable String firstName) throws CustomerDetailsNotFoundException {
        List<Customer> customerByFirstName = customerServices.getAllCustomers();
        return new ResponseEntity<>(customerByFirstName, HttpStatus.OK);
    }

    //Find By First Name
    @GetMapping(value = "/v1/customer/lastName/{lastName}")
    public ResponseEntity <List<Customer>> getCustomerByLastName(@PathVariable String lastName) throws CustomerDetailsNotFoundException {
        List<Customer> customerByLastName = customerServices.getAllCustomers();
        return new ResponseEntity<>(customerByLastName, HttpStatus.OK);
    }



}