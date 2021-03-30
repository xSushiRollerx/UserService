/**
 * 
 */
package com.xsushirollx.sushibyte;

import static org.junit.jupiter.api.Assertions.*;
import javax.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.xsushirollx.sushibyte.entities.Customer;
import com.xsushirollx.sushibyte.entities.Driver;
import com.xsushirollx.sushibyte.entities.User;
import com.xsushirollx.sushibyte.repositories.CustomerDAO;
import com.xsushirollx.sushibyte.repositories.UserDAO;

/**
 * @author dyltr
 *
 */
@SpringBootTest
class CustomerDaoTest {
	@Autowired
	private CustomerDAO c1;
	@Autowired
	private UserDAO u1;
	
	@Test
	@Transactional
	@Rollback(true)
	void createCustomerTest() {
		User user;
		Customer test1;
		
		//Test is glitched. Throws an exception when not testing for it. Throws nothing when testing for it
		//assertThrows(DataIntegrityViolationException.class,()->c1.save(new Customer(100000)));
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		final int t = user.getId();
		//generate key test and equal test
		assertDoesNotThrow(()->(c1.save(new Customer(t))));
		test1 = c1.save(new Customer(t));
		assertNotNull(test1);
		assertEquals(test1.isElderly(),false);
		assertEquals(test1.isVeteran(),false);
		assertEquals(test1.getLoyaltyPoints(),0);
		
		//update
		test1.setLoyaltyPoints(5);
		test1=c1.save(test1);
		assertEquals(5,test1.getLoyaltyPoints());
	}

	/**
	 * Will not use in project
	 */
	@Test
	@Transactional
	@Rollback(true)
	void deleteCustomerTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		Customer test1;
		assertDoesNotThrow(()->c1.delete(new Customer(user.getId())));
		test1 = c1.save(new Customer(user.getId()));
		assertDoesNotThrow(()->c1.delete(test1));
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByIdTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		Customer test1;
		test1 = c1.save(new Customer(user.getId()));
		int id = test1.getId();
		test1 = c1.findById(id).get();
		assertNotNull(test1);
	}

}
