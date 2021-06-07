/**
 * 
 */
package com.xsushirollx.sushibyte.user.dao;

import static org.junit.jupiter.api.Assertions.*;
import javax.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.xsushirollx.sushibyte.user.entities.User;

/**
 * @author dyltr
 * Test user crud operations
 */
@SpringBootTest
class UserDaoTest {
	@Autowired
	private UserDAO userDao;

	@Test
	@Transactional
	@Rollback(true)
	void findByUsernameTest() {
		User test1 = new User();
		test1.setFirstName("first");
		test1.setLastName("last");
		test1.setPhone("phone");
		test1.setEmail("test1");
		test1.setUsername("test1");
		test1.setPassword("password");
		test1 = userDao.save(test1);
		int id = test1.getId();
		test1 = userDao.findByUsername("test1");
		assertNotNull(test1);
		assertEquals(test1.getId(), id);
		assertEquals(test1.getFirstName(), "first");
		assertEquals(test1.getLastName(), "last");
		assertEquals(test1.getEmail(), "test1");
		assertEquals(test1.getUsername(), "test1");
		assertEquals(test1.getPassword(), "password");
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByEmailTest() {
		User test1 = new User();
		test1.setFirstName("first");
		test1.setLastName("last");
		test1.setPhone("phone");
		test1.setEmail("test1");
		test1.setUsername("test1");
		test1.setPassword("password");
		test1 = userDao.save(test1);
		int id = test1.getId();
		test1 = userDao.findByEmail("test1");
		assertNotNull(test1);
		assertEquals(test1.getId(), id);
		assertEquals(test1.getFirstName(), "first");
		assertEquals(test1.getLastName(), "last");
		assertEquals(test1.getEmail(), "test1");
		assertEquals(test1.getUsername(), "test1");
		assertEquals(test1.getPassword(), "password");
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByPhoneTest() {
		User test1 = new User();
		test1.setFirstName("first");
		test1.setLastName("last");
		test1.setPhone("phone");
		test1.setEmail("test1");
		test1.setUsername("test1");
		test1.setPassword("password");
		test1 = userDao.save(test1);
		int id = test1.getId();
		test1 = userDao.findByPhone("phone");
		assertNotNull(test1);
		assertEquals(test1.getId(), id);
		assertEquals(test1.getFirstName(), "first");
		assertEquals(test1.getLastName(), "last");
		assertEquals(test1.getEmail(), "test1");
		assertEquals(test1.getUsername(), "test1");
		assertEquals(test1.getPassword(), "password");
	}

}
