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
import com.xsushirollx.sushibyte.entities.Driver;
import com.xsushirollx.sushibyte.entities.User;
import com.xsushirollx.sushibyte.repositories.DriverDAO;
import com.xsushirollx.sushibyte.repositories.UserDAO;

/**
 * @author dyltr
 *
 */
@SpringBootTest
class DriverDaoTest {
	@Autowired
	private DriverDAO d1;
	@Autowired
	private UserDAO u1;
	
	@Test
	@Transactional
	@Rollback(true)
	void createDriverTest() {
		User user;
		Driver test1;
		
		//should not save if user does not exist
		assertThrows(DataIntegrityViolationException.class,()->d1.save(new Driver(100000)));
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		final int t = user.getId();
		//generate key test and equal test
		assertDoesNotThrow(()->(d1.save(new Driver(t))));
		test1 = d1.save(new Driver(t));
		assertNotNull(test1);
		assertEquals(test1.getRating(),0);
		assertEquals(test1.getTotalDeliveries(),0);
				
		//update
		test1.setRating(5);
		test1=d1.save(test1);
		assertNotEquals(5,test1.getRating());
	}

	/**
	 * Will not use in project
	 */
	@Test
	@Transactional
	@Rollback(true)
	void deleteDriverTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		Driver test1;
		assertDoesNotThrow(()->d1.delete(new Driver(user.getId())));
		test1 = d1.save(new Driver(user.getId()));
		assertDoesNotThrow(()->d1.delete(test1));
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByIdTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1","password"));
		Driver test1;
		test1 = d1.save(new Driver(user.getId()));
		int id = test1.getId();
		test1 = d1.findById(id).get();
		assertNotNull(test1);
	}

}
