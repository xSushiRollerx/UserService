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
import com.xsushirollx.sushibyte.user.dao.UserDAO;
import com.xsushirollx.sushibyte.user.dao.VerificationDAO;
import com.xsushirollx.sushibyte.user.entities.User;

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
	void findByVerificationCodeTest() {
		User test1 = new User();
		test1.setFirstName("first");
		test1.setLastName("last");
		test1.setPhone("phone");
		test1.setEmail("test1");
		test1.setUsername("test1");
		test1.setPassword("password");
		test1 = u1.save(test1);
		Verification test2;
		test2 = d1.save(new Verification(test1.getId()));
		String code = test2.getVerificationCode();
		test2 = d1.findByVericationCode(code);
		assertNotNull(test1);
	}

}
