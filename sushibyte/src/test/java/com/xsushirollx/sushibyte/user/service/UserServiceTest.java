package com.xsushirollx.sushibyte.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.user.configs.PasswordUtils;
import com.xsushirollx.sushibyte.user.dto.AuthorizationDTO;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.entities.Customer;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.entities.Verification;
import com.xsushirollx.sushibyte.user.repositories.CustomerDAO;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;
import com.xsushirollx.sushibyte.user.repositories.VerificationDAO;

@SpringBootTest
class UserServiceTest {
	@InjectMocks
	UserService u1;
	@Mock
	UserDAO m1;
	@Mock
	VerificationDAO v1;
	@Mock
	CustomerDAO c1;

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
		assertTrue(u1.validateEmail("dylan.tran@smoothstack.com"));
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
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@smoothstack.com");
		userDTO.setFirstName("test");
		userDTO.setLastName("test");
		userDTO.setPassword("password");
		userDTO.setUsername("test");
		userDTO.setPhone("1234658394");
		User user = new User();
		when(m1.findByEmail("test@smoothstack.com")).thenReturn(null);
		when(m1.findByUsername("test")).thenReturn(null);
		when(m1.findByPhone("1234658394")).thenReturn(null);
		when(m1.save(Mockito.any(User.class))).thenReturn(user);
		when(c1.save(Mockito.any(Customer.class))).thenReturn(new Customer());
		when(v1.save(Mockito.any(Verification.class))).thenReturn(new Verification(1));
		assertNotNull(u1.registerOnValidation(userDTO));
		assertNull(u1.registerOnValidation(null));
	}
	
	@Test
	void verifyEmailTest() {
		Verification user = new Verification(1);
		user.setCreatedAt(Timestamp.from(Instant.now()));
		when(v1.findByVericationCode("test")).thenReturn(user);
		when(m1.findById(1)).thenReturn(Optional.of(new User()));
		assertFalse(u1.verifyUserEmail(null));
		assertTrue(u1.verifyUserEmail("test"));
	}
	
	@Test
	void logInTest() {
		User user = new User();
		user.setUsername("Dylan");
		String salt = PasswordUtils.getSalt(30);
		user.setPassword(PasswordUtils.generateSecurePassword("password", salt));
		user.setSalt(salt);
		
		User user2 = new User();
		user2.setUsername("Dylan2");
		user2.setPassword(PasswordUtils.generateSecurePassword("password", salt));
		user2.setSalt(salt);
		
		//verify password check is correct
		assertTrue(PasswordUtils.verifyUserPassword("password", user2.getPassword(), salt));
		
		when(m1.findByUsername("Dylan")).thenReturn(user);
		assertNotNull(u1.logIn("Dylan", "password"));
		when(m1.findByEmail("Dylan@gmail.com")).thenReturn(user2);
		assertNotNull(u1.logIn("Dylan@gmail.com", "password"));
	}
	
	@Test
	void getAuthorizationTest() {
		u1.loggedUsers.put(10, new AuthorizationDTO(1,3));
		u1.loggedUsers.put(11, new AuthorizationDTO(2,2));
		u1.loggedUsers.put(12, new AuthorizationDTO(3,1));
		assertEquals(u1.getAuthorization(10).getUserRole(),3);
		assertEquals(u1.getAuthorization(11).getUserRole(),2);
		assertEquals(u1.getAuthorization(12).getUserRole(),1);
	}
	
	@Test
	void logOutTest() {
		u1.loggedUsers.put(10, new AuthorizationDTO(1,3));
		assertFalse(u1.logOut(null));
		assertTrue(u1.logOut(10));
	}
	
	@Test
	void resetVerificationCodeTest() throws Exception {
		when(m1.findByEmail("email")).thenReturn(new User());
		when(v1.save(Mockito.any(Verification.class))).thenReturn(new Verification(1));
		assertNotNull(u1.resetVerificationCode("email"));
	}
	
}
