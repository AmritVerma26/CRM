package com.maveric.crm.tests;

import com.maveric.crm.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crm.pojos.Customer;
import com.maveric.crm.services.CustomerServices;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = com.maveric.crm.CrmAppApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class CrmAppApplicationTests {

	@Autowired
	CustomerServices customerServices;

	@Test
	@Order(1)
	void testCustomerAcceptance() {
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Amrit", "Verma","av@gmail.com", "Male", 23)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Aarzoo", "Verma","arv@gmail.com", "Female", 24)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Reeta", "Verma","rv@gmail.com", "Female", 50)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Reeya", "Chaudhary", "rcc@gmail.com", "female", 22)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Vinay" , "Mallik" , "vm@gmail.com","Male", 25)));
	}

	@Test
	@Order(2)
	void testCustomerById_Positive() throws CustomerDetailsNotFoundException {
		assertEquals("Amrit", customerServices.getCustomerDetailsById(1).getFirstName());
	}

	@Test
	@Order(3)
	void testCustomerById_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerDetailsById(10));
	}


	@Test
	@Order(4)
	void validFirstName_Positive() throws CustomerDetailsNotFoundException {
		Customer expectedCustomer = new Customer(4, "Reeya", "Chaudhary", "rcc@gmail.com", "female", 22);
		Customer actualCustomer = customerServices.getCustomerByFirstName("Reeya");
		assertEquals(expectedCustomer,actualCustomer);
	}

	@Test
	@Order(5)
	void invalidFirstName_Negative(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerByFirstName("hello"));
	}

	@Test
	@Order(6)
	void validLastName_Positive() {
		Customer expectedCustomer = new Customer(5, "Vinay" , "Mallik" , "vm@gmail.com","Male", 25);
		Customer actualCustomer = customerServices.getCustomerByLastName("Mallik");
		assertEquals(expectedCustomer,actualCustomer);
	}

	@Test
	@Order(7)
	void invalidLastName_Negative(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class,()->customerServices.getCustomerByLastName("Pandey"));
	}


	@Test
	@Order(8)
	void updateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
		Customer customerToUpdate = customerServices.getCustomerDetailsById(1);
		customerToUpdate.setFirstName("Amrit Updated");
		customerServices.updateCustomerDetails(customerToUpdate);
		assertEquals("Amrit Updated", customerServices.getCustomerDetailsById(1).getFirstName());
	}

	@Test
	@Order(9)
	void updateCustomerDetails_Negative() {
		Customer nonExistentCustomer = new Customer(100, "Japan", "USA", "jsad@gmail.com", "hi", 30);
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.updateCustomerDetails(nonExistentCustomer));
	}


	@Test
	@Order(10)
	void removeCustomerDetailsById_Positive() throws CustomerDetailsNotFoundException {
		customerServices.removeCustomerDetailsById(4);
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerDetailsById(4));
	}

	@Test
	@Order(11)
	void removeCustomerDetailsById_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, ()-> customerServices.getCustomerDetailsById(100));
	}

}
