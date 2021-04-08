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
import com.xsushirollx.sushibyte.user.entities.Verification;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.repositories.VerificationDAO;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;

/**
 * @author dyltr
 * Test Verification crud operations
 */
@SpringBootTest
class VerificationDaoTest {
	@Autowired
	private VerificationDAO d1;
	@Autowired
	private UserDAO u1;

	@Test
	@Transactional
	@Rollback(true)
	void createVerificationTest() {
		User user;
		Verification test1;
		user = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		final int t = user.getId();
		// generate key test and equal test
		assertDoesNotThrow(() -> (d1.save(new Verification(t))));
		test1 = d1.save(new Verification(t));
		assertNotNull(test1);
		assertNotNull(test1.getCreatedAt());
		assertNotNull(test1.getVerificationCode());

		// update
		test1.setVerificationCode("code");
		test1 = d1.save(test1);
		assertEquals("code", test1.getVerificationCode());
	}

	/**
	 * Will not use in project
	 */
	@Test
	@Transactional
	@Rollback(true)
	void deleteVerificationTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		Verification test1;
		assertDoesNotThrow(() -> d1.delete(new Verification(user.getId())));
		test1 = d1.save(new Verification(user.getId()));
		assertDoesNotThrow(() -> d1.delete(test1));
	}

	@Test
	@Transactional
	@Rollback(true)
	void findByIdTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		Verification test1;
		test1 = d1.save(new Verification(user.getId()));
		int id = test1.getId();
		test1 = d1.findById(id).get();
		assertNotNull(test1);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	void findByVerificationCodeTest() {
		User user;
		user = u1.save(new User("first", "last", "phone", "test1", "test1", "password"));
		Verification test1;
		test1 = d1.save(new Verification(user.getId()));
		String code = test1.getVerificationCode();
		test1 = d1.findByVericationCode(code);
		assertNotNull(test1);
	}

}
