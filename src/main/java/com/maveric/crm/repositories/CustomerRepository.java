package com.maveric.crm.repositories;

import com.maveric.crm.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    List<Customer> findByAge(int age);
    List<Customer> findByGender(String gender);

    @Override
    List<Customer> findAll();

    Customer findByFirstName(String firstName);
    Customer findByLastName(String lastName);


}
