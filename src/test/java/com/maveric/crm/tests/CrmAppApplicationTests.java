package com.maveric.crm.tests;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.repositories.CustomerRepository;
import com.maveric.crm.services.CustomerServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.maveric.crm.CrmAppApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class CrmAppApplicationTests {

	@MockitoBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerServices customerServices;

	@BeforeEach
	void setUp() {
		Customer customer1 = new Customer(1, "Amrit", "Verma", "av@gmail.com", "Male", 23);
		Customer customer2 = new Customer(2, "Ram", "Kumar", "rk@gmail.com", "Male", 30);
		Customer customer3 = new Customer(3, "Shyam,", "Patel", "sp@gmail.com", "Male", 36);

		//save
		Mockito.when(customerRepository.save(new Customer("Amrit", "Verma","av@gmail.com", "Male", 23))).thenReturn(customer1);
		Mockito.when(customerRepository.save(new Customer("Ram", "Kumar","rk@gmail.com", "Male", 30))).thenReturn(customer2);
		Mockito.when(customerRepository.save(new Customer("Shyam", "Patel","sp@gmail.com", "Male", 36))).thenReturn(customer3);

		//findById
		Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));
		Mockito.when(customerRepository.findById(2)).thenReturn(Optional.of(customer2));
		Mockito.when(customerRepository.findById(3)).thenReturn(Optional.of(customer3));
		Mockito.when(customerRepository.findById(100)).thenReturn(Optional.ofNullable(null));

		//findAll
		List<Customer> customers = List.of(customer1, customer2, customer3);
		Mockito.when(customerRepository.findAll()).thenReturn(customers);

		//findByFirstName
		Mockito.when(customerRepository.findByFirstName("Shyam")).thenReturn(List.of(customer3));
		Mockito.when(customerRepository.findByFirstName("abc")).thenReturn(Collections.emptyList());

		//findByLastName
		Mockito.when(customerRepository.findByLastName("Verma")).thenReturn(List.of(customer1));
		Mockito.when(customerRepository.findByLastName("Casga")).thenReturn(emptyList());

		//update
		Customer updatedCustomer = new Customer(2,"Reeya","C","reeyac@gmail.com","Female",20);
		Mockito.when(customerRepository.save( new Customer(2,"Reeya","C","reeyac@gmail.com","Female",20))).thenReturn(updatedCustomer);
		Mockito.when(customerRepository.save( new Customer(99,"Reeya","C","reeyac@gmail.com","Female",20))).thenReturn(null);

		//deleteById
		doNothing().when(customerRepository).deleteById(1);
		doNothing().when(customerRepository).deleteById(999);

	}

	@Test
	@Order(1)
	void testCustomerAcceptance() {
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Amrit", "Verma","av@gmail.com", "Male", 23)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Ram", "Kumar","rk@gmail.com", "Male", 30)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Shyam", "Patel","sp@gmail.com", "Male", 36)));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(Mockito.any(Customer.class));
	}

	@Test
	@Order(2)
	void testCustomerById_Positive() throws CustomerDetailsNotFoundException {
		Customer expectedCustomer = new Customer(1,"Amrit", "Verma","av@gmail.com", "Male", 23);
		assertEquals(expectedCustomer, customerServices.getCustomerDetailsById(1));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(1);

	}

	@Test
	@Order(3)
	void testCustomerById_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerDetailsById(10));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(10);
	}


	@Test
	@Order(4)
	void validFirstName_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> expectedCustomer = List.of(new Customer(3, "Shyam,", "Patel", "sp@gmail.com", "Male", 36));
		List<Customer> actualCustomer = customerServices.getCustomerByFirstName("Shyam");
		assertEquals(expectedCustomer,actualCustomer);
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstName("Shyam");
	}

	@Test
	@Order(5)
	void invalidFirstName_Negative(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerByFirstName("hello"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstName("hello");

	}

	@Test
	@Order(6)
	void validLastName_Positive() {
		List<Customer> expectedCustomer = List.of(new Customer(1, "Amrit" , "Verma" , "av@gmail.com","Male", 23));
		List<Customer> actualCustomer = customerServices.getCustomerByLastName("Verma");
		assertEquals(expectedCustomer,actualCustomer);
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByLastName("Verma");

	}

	@Test
	@Order(7)
	void invalidLastName_Negative(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class,()->customerServices.getCustomerByLastName("Pandey"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByLastName("Pandey");
	}

	@Test
	@Order(8)
	void updateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
		Customer existingCustomer = new Customer(2,"Reeya","C","reeyac@gmail.com","Female",20);
		Assertions.assertEquals(existingCustomer, customerServices.updateCustomerDetails(existingCustomer));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(existingCustomer);
	}

	@Test
	@Order(9)
	void updateCustomerDetails_Negative() {
		Customer nonExistentCustomer = new Customer(999,"Reeya","C","reeyac@gmail.com","Female",20);
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.updateCustomerDetails(nonExistentCustomer));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(999);

	}


	@Test
	@Order(10)
	void removeCustomerDetailsById_Positive() throws CustomerDetailsNotFoundException {
	    customerServices.removeCustomerDetailsById(2);
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).deleteById(2);
	}

	@Test
	@Order(11)
	void removeCustomerDetailsById_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.removeCustomerDetailsById(999));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(999);
	}

}
