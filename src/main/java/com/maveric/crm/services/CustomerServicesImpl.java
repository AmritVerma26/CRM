package com.maveric.crm.services;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("customerServices")
public class CustomerServicesImpl implements CustomerServices{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer acceptCustomerDetails(Customer customer) {
        return customerRepository.save(customer) ;
    }

    @Override
    public Customer updateCustomerDetails(Customer customer) throws CustomerDetailsNotFoundException {
        this.getCustomerDetailsById(customer.getId());
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new CustomerDetailsNotFoundException("No customers found.");
        }
        return customers;
    }


    @Override
    public void removeCustomerDetailsById(int id) throws CustomerDetailsNotFoundException {
        this.getCustomerDetailsById(id);
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerDetailsById(int id) throws CustomerDetailsNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()) throw new CustomerDetailsNotFoundException("Customer with id : " +id + " not found.");
        return customerOptional.get();
    }

    @Override
    public List<Customer> getCustomerDetailsByAge(int age) throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerRepository.findByAge(age);
        if (customers.isEmpty()) throw new CustomerDetailsNotFoundException("Customer with age : " +age + " not found.");
        return customers;
    }

    @Override
    public List<Customer> getCustomerDetailsByGender(String gender) throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerRepository.findByGender(gender);
        if (customers.isEmpty()) throw new CustomerDetailsNotFoundException("No customers found for the gender : " +gender);
        return customers;
    }

    @Override
    public List<Customer> getCustomerByFirstName(String firstName) throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerRepository.findByFirstName(firstName);
        if (customers.isEmpty())
            throw new CustomerDetailsNotFoundException("Customer with first name " + firstName + " not found");
        return customers;
    }

    @Override
    public List<Customer> getCustomerByLastName(String lastName) throws CustomerDetailsNotFoundException {
        List<Customer> customers = customerRepository.findByLastName(lastName);
        if (customers.isEmpty())
            throw new CustomerDetailsNotFoundException("No customers found with Last Name: " + lastName);
        return customers;
    }
}
