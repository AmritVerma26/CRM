package com.maveric.avcrm.services;

import com.maveric.avcrm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.avcrm.pojos.Customer;
import com.maveric.avcrm.repositories.CustomerRepository;
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
    public void updateCustomerDetails(Customer customer) throws CustomerDetailsNotFoundException {
        this.getCustomerDetailsById(customer.getId());
        customerRepository.save(customer);
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
        if (customers.isEmpty()) throw new CustomerDetailsNotFoundException("Customer : " +gender + " not found.");
        return customers;
    }
}
