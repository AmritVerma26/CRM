package com.maveric.crm.services;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;

import java.util.List;

public interface CustomerServices {

    Customer acceptCustomerDetails(Customer customer);

    Customer updateCustomerDetails(Customer customer) throws CustomerDetailsNotFoundException;

    List<Customer> getAllCustomers() throws CustomerDetailsNotFoundException;

    void removeCustomerDetailsById(int id) throws CustomerDetailsNotFoundException;

    Customer getCustomerDetailsById(int id) throws CustomerDetailsNotFoundException;

    List<Customer> getCustomerDetailsByAge(int age) throws CustomerDetailsNotFoundException;

    List<Customer> getCustomerDetailsByGender(String gender) throws CustomerDetailsNotFoundException;

    List <Customer> getCustomerByFirstName(String firstName) throws CustomerDetailsNotFoundException;

    List <Customer> getCustomerByLastName(String lastName) throws CustomerDetailsNotFoundException;

}
