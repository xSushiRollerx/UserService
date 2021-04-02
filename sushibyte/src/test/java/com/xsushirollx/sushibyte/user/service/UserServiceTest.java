package com.xsushirollx.sushibyte.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;

@SpringBootTest
class UserServiceTest {
	@InjectMocks
	UserService u1;
	@Mock
	UserDAO m1;

	@Test
	void validateNameTest() {
		assertEquals(u1.validateName("Hello"), true);
		assertEquals(u1.validateName("Hello1"), false);
	}

	@Test
	void validatePasswordTest() {
		assertEquals(u1.validatePassword("Hello1"), true);
		// password too short
		assertEquals(u1.validatePassword("Hello"), false);
		// password too long
		assertEquals(u1.validatePassword("Hello12345678912345612"), false);
	}

	@Test
	void validatePhoneTest() {
		User user = new User();
		when(m1.findByPhone("1233219900")).thenReturn(null);
		assertTrue(u1.validatePhone("1233219900"));
		when(m1.findByPhone("23124241231")).thenReturn(user);
		assertFalse(u1.validatePhone("23124241231"));
		when(m1.findByPhone("2312424ds2")).thenReturn(user);
		assertFalse(u1.validatePhone("2312424ds2"));
		when(m1.findByPhone("2312424")).thenReturn(user);
		assertFalse(u1.validatePhone("2312424"));
		when(m1.findByPhone("1233219900")).thenReturn(user);
		assertFalse(u1.validatePhone("1233219900"));
	}

	@Test
	void validateEmailTest() {
		User user = new User();
		when(m1.findByEmail("dylan.tran@smoothstack.com")).thenReturn(null);
		assertTrue(u1.validateEmail("dylan.tran@smoothstack.com"));
		when(m1.findByEmail("dylan.tran@smoothstack.com")).thenReturn(user);
		assertFalse(u1.validateEmail("dylan.tran@smoothstack.com"));
	}

	@Test
	void validateUsernameTest() {
		User user = new User();
		when(m1.findByUsername("test")).thenReturn(null);
		assertTrue(u1.validateUsername("test"));
		when(m1.findByUsername("test")).thenReturn(user);
		assertFalse(u1.validateUsername("test"));
	}
	
	@Test
	void registerOnValidationTest() {
		User user = new User();
		user.setVerificationCode("code");
		when(m1.save(Mockito.any(User.class))).thenReturn(user);
		assertNotNull(u1.registerOnValidation("Dylan", "Tran", "0000000000", "dylan.tran@smoothstack.com", 
				"dyltra", "password"));
		assertNull(u1.registerOnValidation("Dylan", "Tran", "0000000000", "dylan.tran@smoothstack.com", 
				"dyltra", "passw"));
	}
	
	@Test
	void verifyEmailTest() {
		User user = new User();
		when(m1.findByVericationCode("test")).thenReturn(user);
		assertFalse(u1.verifyUserEmail(null));
		assertTrue(u1.verifyUserEmail("test"));
	}
	
}
