/**
 * 
 */
package com.xsushirollx.sushibyte.user.dao;

import static org.junit.jupiter.api.Assertions.*;
import javax.transaction.Transactional;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;

/**
 * @author dyltr
 * Test user crud operations
 */
@SpringBootTest
class UserDaoTest {
	@Autowired
	private UserDAO u1;

	@Test
	@Transactional
	@Rollback(true)
	void createUserTest() {
		User test1;
		// generate key test and equal test
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		assertNotNull(test1);
		assertNotEquals(test1.getId(), 0);
		assertEquals(test1.getFirstName(), "first");
		assertEquals(test1.getLastName(), "last");
		assertEquals(test1.getEmail(), "test1");
		assertEquals(test1.getUsername(), "test1");
		assertEquals(test1.getPassword(), "password");

		// Cannot insert on unique columns: username, phone and email
		assertThrows(DataIntegrityViolationException.class,
				() -> u1.save(new User("first", "last", "phone", "test2", "test2", "password")));
		assertThrows(DataIntegrityViolationException.class,
				() -> u1.save(new User("first", "last", "phone2", "test1", "test2", "password")));
		assertThrows(DataIntegrityViolationException.class,
				() -> u1.save(new User("first", "last", "phone2", "test2", "test1", "password")));

		// update
		test1.setEmail("testtest1");
		test1 = u1.save(test1);
		assertNotEquals("test1", test1.getEmail());
	}

	/**
	 * Will not use in project
	 */
	@Test
	@Transactional
	@Rollback(true)
	void deleteUserTest() {
		User test1;
		assertDoesNotThrow(() -> u1.delete(new User("first", "last", "phone", "test1", "test1", "password")));
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		assertDoesNotThrow(() -> u1.delete(test1));
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByIdTest() {
		User test1;
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		int id = test1.getId();
		test1 = u1.findById(id).get();
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
	void findByUsernameTest() {
		User test1;
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		int id = test1.getId();
		test1 = u1.findByUsername("test1");
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
		User test1;
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		int id = test1.getId();
		test1 = u1.findByEmail("test1");
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
		User test1;
		test1 = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		int id = test1.getId();
		test1 = u1.findByPhone("phone");
		assertNotNull(test1);
		assertEquals(test1.getId(), id);
		assertEquals(test1.getFirstName(), "first");
		assertEquals(test1.getLastName(), "last");
		assertEquals(test1.getEmail(), "test1");
		assertEquals(test1.getUsername(), "test1");
		assertEquals(test1.getPassword(), "password");
	}

}
