package com.maveric.crm.repositories;

import com.maveric.crm.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    List<Customer> findByAge(int age);
    List<Customer> findByGender(String gender);

    List<Customer> findAll();

    List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);


}
