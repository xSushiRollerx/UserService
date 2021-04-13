package com.xsushirollx.sushibyte.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
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
	UserService userService;
	@Mock
	UserDAO userDAO;
	@Mock
	VerificationDAO verificationDAO;
	@Mock
	CustomerDAO customerDAO;

	@Test
	void validateNameTest() {
		assertEquals(userService.validateName("Hello"), true);
		assertEquals(userService.validateName("Hello1"), false);
	}

	@Test
	void validatePasswordTest() {
		assertEquals(userService.validatePassword("Hello1"), true);
		// password too short
		assertEquals(userService.validatePassword("Hello"), false);
		// password too long
		assertEquals(userService.validatePassword("Hello12345678912345612"), false);
	}

	@Test
	void validatePhoneTest() {
		assertTrue(userService.validatePhone("1233219900"));
		assertFalse(userService.validatePhone("23124241231"));
		assertFalse(userService.validatePhone("2312424ds2"));
		assertFalse(userService.validatePhone("2312424"));
	}

	@Test
	void validateEmailTest() {
		assertTrue(userService.validateEmail("dylan.tran@smoothstack.com"));
	}

	@Test
	void validateUsernameTest() {
		assertTrue(userService.validateUsername("test"));
		assertFalse(userService.validateUsername(""));
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
		when(userDAO.findByEmail("test@smoothstack.com")).thenReturn(null);
		when(userDAO.findByUsername("test")).thenReturn(null);
		when(userDAO.findByPhone("1234658394")).thenReturn(null);
		when(userDAO.save(Mockito.any(User.class))).thenReturn(user);
		when(customerDAO.save(Mockito.any(Customer.class))).thenReturn(new Customer());
		when(verificationDAO.save(Mockito.any(Verification.class))).thenReturn(new Verification(1));
		assertNotNull(userService.registerOnValidation(userDTO));
		assertNull(userService.registerOnValidation(null));
	}
	
	@Test
	void verifyEmailTest() {
		Verification user = new Verification(1);
		user.setCreatedAt(Timestamp.from(Instant.now()));
		when(verificationDAO.findByVericationCode("test")).thenReturn(user);
		when(userDAO.findById(1)).thenReturn(Optional.of(new User()));
		assertFalse(userService.verifyUserEmail(null));
		assertTrue(userService.verifyUserEmail("test"));
	}
	
	@Test
	void resetVerificationCodeTest() throws Exception {
		when(userDAO.findByEmail("email")).thenReturn(new User());
		when(verificationDAO.save(Mockito.any(Verification.class))).thenReturn(new Verification(1));
		assertNotNull(userService.resetVerificationCode("email"));
	}
	
	@Test
	void closeAccountTest() {
		Optional<User> user = Optional.of(new User());
		when(userDAO.existsById(10)).thenReturn(true);
		when(userDAO.findById(10)).thenReturn(user);
		userService.closeAccount(10);
		assertEquals(false,user.get().isActive());		
	}
	
	@Test
	void reactivateAccountTest() {
		Optional<User> user = Optional.of(new User());
		when(userDAO.existsById(10)).thenReturn(true);
		when(userDAO.findById(10)).thenReturn(user);
		userService.reactivateAccount(10);
		assertEquals(true,user.get().isActive());
	}
	
	@Test
	void updateAccountTest() {
		Optional<User> user = Optional.of(new User());
		UserDTO userD = new UserDTO();
		userD.setEmail("test@test.com");
		userD.setFirstName("Test");
		userD.setLastName("Test");
		userD.setPassword("testing");
		userD.setUsername("test");
		userD.setPhone("1234567891");
		when(userDAO.existsById(10)).thenReturn(true);
		when(userDAO.findById(10)).thenReturn(user);
		assertTrue(userService.updateAccount(10,userD));
	}
	
	@Test
	void getUsertInfoTest() {
		Optional<User> user = Optional.of(new User());
		when(userDAO.existsById(10)).thenReturn(true);
		when(userDAO.findById(10)).thenReturn(user);
		assertEquals(userService.getUserInfo(10).getClass(),UserDTO.class);
	}
	
}
