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

import com.xsushirollx.sushibyte.user.dto.DriverDTO;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.entities.Customer;
import com.xsushirollx.sushibyte.user.entities.Driver;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.entities.Verification;
import com.xsushirollx.sushibyte.user.repositories.CustomerDAO;
import com.xsushirollx.sushibyte.user.repositories.DriverDAO;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;
import com.xsushirollx.sushibyte.user.repositories.VerificationDAO;

@SpringBootTest
class UserServiceTest {
	@InjectMocks
	UserService userService;
	@Mock
	UserDAO userDAO;
	@Mock
	DriverDAO driverDAO;
	@Mock
	VerificationDAO verificationDAO;
	@Mock
	CustomerDAO customerDAO;
	
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
		when(userDAO.findByUsername("10")).thenReturn(user.get());
		userService.closeAccount("10");
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
		when(userDAO.findByUsername("10")).thenReturn(user.get());
		assertTrue(userService.updateAccount("10",userD));
	}
	
	@Test
	void updateAccountRoleTest() {
		Optional<User> user = Optional.of(new User());
		user.get().setUserRole(1);
		when(userDAO.findByUsername("10")).thenReturn(user.get());
		assertTrue(userService.updateAccountRole("10", 3));
		assertEquals(3, user.get().getUserRole());
		assertTrue(userService.updateAccountRole("10", 2));
		assertEquals(2, user.get().getUserRole());
		assertTrue(userService.updateAccountRole("10", 1));
		assertEquals(1, user.get().getUserRole());
	}
	
	@Test
	void getUsertInfoTest() {
		User user = new User();
		when(userDAO.findByUsername("10")).thenReturn(user);
		assertEquals(userService.getUserInfo("10").getClass(),UserDTO.class);
	}
	
	@Test
	void readDriverTest( ) {
		User user = new User();
		user.setId(10);
		when(userDAO.findByUsername("10")).thenReturn(user);
		when(driverDAO.findById(10)).thenReturn(Optional.of(new Driver()));
		assertEquals(userService.readDriver("10").getClass(),DriverDTO.class);
	}
	
	@Test
	void reactivateDriverTest( ) {
		User user = new User();
		user.setId(10);
		when(userDAO.findByUsername("10")).thenReturn(user);
		Driver driver = new Driver();
		driver.setIsActive(false);
		when(driverDAO.findById(10)).thenReturn(Optional.of(driver));
		assertTrue(userService.reactivateDriver("10"));
		assertFalse(userService.reactivateDriver("10"));
	}
	
	@Test
	void deactivateDriverTest( ) {
		User user = new User();
		user.setId(10);
		when(userDAO.findByUsername("10")).thenReturn(user);
		Driver driver = new Driver();
		driver.setIsActive(true);
		when(driverDAO.findById(10)).thenReturn(Optional.of(driver));
		assertTrue(userService.deactivateDriver("10"));
		assertFalse(userService.deactivateDriver("10"));
	}
	
}
