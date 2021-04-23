package com.xsushirollx.sushibyte.user.service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xsushirollx.sushibyte.user.configs.PasswordUtils;
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

/**
 * @author dyltr 
 * references: https://www.codejava.net/frameworks/spring-boot/email-verification-example
 * Provide business logic for general users        
 */
@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CustomerDAO customerDAO;
	@Autowired
	private DriverDAO driverDAO;
	@Autowired
	private VerificationDAO verificationDAO;
	//users mapped with user role
	static Logger log = LogManager.getLogger(UserService.class.getName());

	/**
	 * @param userDTO TODO
	 * @param string 
	 * @param user
	 * @return true if user has successfully been saved
	 */
	@Transactional
	public String registerOnValidation(UserDTO userDTO) {
		if (userDTO==null) {
			return null;
		}
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setPhone(userDTO.getPhone());
		user.setUserRole(2);
		if (!validatePassword(user.getPassword())||
				!validateName(user.getFirstName())||
				!validateName(user.getLastName())||
				!validateEmail(user.getEmail())||
				checkEmailExist(user.getEmail())||
				checkPhoneExist(user.getPhone())||
				checkUsernameExist(user.getUsername())||
				!validateUsername(user.getUsername())||
				!validatePhone(user.getPhone())) {
			return null;
		}
		user.setPassword(PasswordUtils.generateSecurePassword(user.getPassword()));
		user.setCreatedAt(Timestamp.from(Instant.now()));
		user.setUserRole(3);
		Verification verification = null;
		try {
			// email validated with hibernate
			user = userDAO.save(user);
			customerDAO.save(new Customer(user.getId()));
			verification=verificationDAO.save(new Verification(user.getId()));
		} catch (Exception e) {
			log.log(Level.WARN, e.getMessage());
		}
		return (verification!=null)?verification.getVerificationCode():null;
	}

	/**
	 * @param email
	 * @return true if meets criteria
	 */
	public boolean validateEmail(String email) {
		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(email).matches()) {
			return true;
		}
		log.debug("Email check failed");
		return false;
	}
	
	/**
	 * @param email
	 * @return true if email exists in database
	 */
	private boolean checkEmailExist(String email) {
		return(userDAO.findByEmail(email) != null)?true:false;
	}
	
	/**
	 * @param email
	 * @return true if phone exists in database
	 */
	private boolean checkPhoneExist(String phone) {
		return(userDAO.findByPhone(phone) != null)?true:false;
	}
	
	/**
	 * @param email
	 * @return true if email exists in database
	 */
	private boolean checkUsernameExist(String username) {
		return(userDAO.findByUsername(username) != null)?true:false;
	}

	/**
	 * @param username
	 * @return true if meets criteria and is unique
	 */
	private boolean validateUsername(String username) {
		if (username == null||username.equals("")) {
			log.debug("Username check failed");
			return false;
		}
		return true;
	}

	/**
	 * @param phone
	 * @return true if meets criteria and is unique
	 */
	private boolean validatePhone(String phone) {
		if (phone == null || phone.length() != 10) {
			log.debug("Phone check failed");
			return false;
		}
		for (char i : phone.toCharArray()) {
			if (!Character.isDigit(i))
				return false;
		}
		return true;
	}

	/**
	 * @param name
	 * @return true if meets criteria
	 */
	private boolean validateName(String name) {
		Pattern pattern = Pattern.compile("[0-9]");
		if (name == null || pattern.matcher(name).find()) {
			log.debug("Name check failed");
			return false;
		}
		return true;
	}

	/**
	 * @param password
	 * @return true if meets criteria
	 */
	private boolean validatePassword(String password) {
		if (password == null || password.length() < 6 || password.length() > 20) {
			log.debug("Password check failed");
			return false;
		}
		return true;
	}

	/**
	 * removes verification code and set active
	 * @param verificationCode
	 * @return true if email still exists and is currently not active
	 */
	@Transactional
	public boolean verifyUserEmail(String verificationCode) {
		Verification verification = verificationDAO.findByVericationCode(verificationCode);
		if (verification == null) {
			return false;
		}
		if (Duration.between(Instant.now(), verification.getCreatedAt().toInstant()).toMinutes()>10) {
			log.log(Level.DEBUG,"Verification code has expired");
			return false;
		}
		User user = userDAO.findById(verification.getId()).get();
		if (user.isActive()) {
			return false;
		}
		user.setActive(true);
		try{
			userDAO.save(user);
			verificationDAO.delete(verification);
		}
		catch(Exception e) {
			log.log(Level.WARN, e.getMessage() + ":\tVerify user email failed");
		}
		return true;
	}
	
	@Transactional
	public String resetVerificationCode(String email) {
		User user = userDAO.findByEmail(email);
		if (user==null) {
			log.warn("Email not found");
			return null;
		}
		if (user.isActive()) {
			log.warn("User already active");
			return null;
		}
		Verification verification = new Verification(user.getId());
		verification = verificationDAO.save(verification);
		return verification.getVerificationCode();
	}

	/**
	 * Don't want to use for now
	 * @param user
	 * @return true if user is deleted or not exist
	 */
	@Transactional
	public boolean deleteUserPermanent(Integer id) {
		if (id==null) {
			return false;
		}
		try {
			if(verificationDAO.existsById(id)) {
				verificationDAO.deleteById(id);
			}
			if (driverDAO.existsById(id)) {
				driverDAO.deleteById(id);
			}
			if (customerDAO.existsById(id)) {
				customerDAO.deleteById(id);
			}
			if (userDAO.existsById(id)) {
				userDAO.deleteById(id);
			}
		} catch (Exception e) {
			log.log(Level.INFO, "User not deleted");
			return false;
		}
		log.log(Level.INFO, "User deleted");
		return true;
	}

	/**
	 * @param userId
	 * @return true if account has successfully been deactivated
	 */
	@Transactional
	public boolean closeAccount(String userId) {	
		User user = userDAO.findByUsername(userId);
		if (user!=null) {
			user.setActive(false);
			try {
				userDAO.save(user);
			}
			catch(Exception e) {
				log.log(Level.WARN, e.getMessage());
				return false;
			}
			return true;
		}
		log.log(Level.INFO,"Id " + userId + " does not exist in database");
		return false;
	}
	
	/**
	 * @param id
	 * @return true if account has successfully been reactivated
	 */
	@Transactional
	public boolean reactivateAccount(Integer id) {
		if (userDAO.existsById(id)) {
			User user = userDAO.findById(id).get();
			user.setActive(true);
			try {
				userDAO.save(user);
			}
			catch(Exception e) {
				log.log(Level.WARN, e.getMessage());
				return false;
			}
			return true;
		}
		log.log(Level.INFO,"Id " + id + " does not exist in database");
		return false;
	}
	
	/**
	 * @param userId
	 * @param userD
	 * @return true if account has successfully updated
	 */
	@Transactional
	public boolean updateAccount(String userId, UserDTO userD) {
		if (!validatePassword(userD.getPassword())||
				!validateName(userD.getFirstName())||
				!validateName(userD.getLastName())||
				!validateEmail(userD.getEmail())||
				!validateUsername(userD.getUsername())||
				!validatePhone(userD.getPhone())) {
			log.warn("user fields not valid");
			return false;
		}
		User user = userDAO.findByUsername(userId);
		if (user!=null) {
			user.setEmail(userD.getEmail());
			user.setFirstName(userD.getFirstName());
			user.setLastName(userD.getLastName());
			user.setPassword(PasswordUtils.generateSecurePassword(userD.getPassword()));
			user.setPhone(userD.getPhone());
			try {
				userDAO.save(user);
			}
			catch(Exception e){
				log.log(Level.WARN,e.getMessage());
				return false;
			}
			return true;
		}
		log.warn("User does not exist");
		return false;
	}
	
	public UserDTO getUserInfo(String username) {
		User user = userDAO.findByUsername(username);
		if (user!=null) {
			UserDTO userD = new UserDTO();
			userD.setUsername(user.getUsername());
			userD.setEmail(user.getEmail());
			userD.setFirstName(user.getFirstName());
			userD.setLastName(user.getLastName());
			//Will not display password as it is hashed anyways
			//userD.setPassword(user.getPassword());
			userD.setPhone(user.getPhone());
			return userD;
		}
		return null;
	}

	@Transactional
	public boolean updateAccountRole(String userId, Integer roleId) {
		// if new driver, create in driver repo
		// if removing driver, set drive as inactive
		User user = userDAO.findByUsername(userId);
		if (user==null) {
			log.log(Level.WARN,"User by id of {0} does not exist",userId);
			return false;
		}
		try {
			if (user.getUserRole()==roleId) {
				log.log(Level.WARN, "User by id of {0} already has role of {1}",userId,roleId);
				return false;
			}
			user.setUserRole(roleId);
			userDAO.save(user);
			//driver check
			if (roleId == 2) {
				Driver driver;
				if (driverDAO.existsById(user.getId())) {
					//set active
					driver=driverDAO.findById(user.getId()).get();
					driver.setIsActive(true);
					return true;
				}
				else {
					//create driver
					driver = new Driver();
					driver.setId(user.getId());
					driverDAO.save(driver);
					return true;
				}
			}
			else {
				return true;
			}
		}
		catch(Exception e){
			log.log(Level.WARN, e.getMessage());
		}
		return false;
	}
	
	public boolean reactivateDriver(String username) {
		User user = userDAO.findByUsername(username);
		if (user!=null) {
			Driver driver = driverDAO.findById(user.getId()).get();
			if (driver!=null) {
				if(!driver.getIsActive()) {
					driver.setIsActive(true);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deactivateDriver(String username) {
		User user = userDAO.findByUsername(username);
		if (user!=null) {
			Driver driver = driverDAO.findById(user.getId()).get();
			if (driver!=null) {
				if(driver.getIsActive()) {
					driver.setIsActive(false);
					return true;
				}
			}
		}
		return false;
	}
	
	public DriverDTO readDriver(String username) {
		User user = userDAO.findByUsername(username);
		if (user!=null) {
			Driver driver = driverDAO.findById(user.getId()).get();
			if (driver!=null) {
				DriverDTO driverDTO = new DriverDTO();
				driverDTO.setFirstName(user.getFirstName());
				driverDTO.setPhone(user.getPhone());
				driverDTO.setRating(driver.getRating());
				driverDTO.setTotalDeliveries(driver.getTotalDeliveries());
				driverDTO.setUsername(username);
				return driverDTO;
			}
		}
		return null;
	}
	
	@Transactional
	public boolean updateDriver(DriverDTO updatedDriver, String username) {
		User user = userDAO.findByUsername(username);
		if (user!=null) {
			Driver driver = driverDAO.findById(user.getId()).get();
			if (driver!=null) {
				user.setUsername(updatedDriver.getUsername());
				user.setPassword(updatedDriver.getPassword());
				user.setPhone(updatedDriver.getPhone());
				driver.setRating(updatedDriver.getRating());
				driver.setTotalDeliveries(updatedDriver.getTotalDeliveries());
				try {
					driverDAO.save(driver);
					userDAO.save(user);
					return true;
				}
				catch(Exception e) {
					log.warn(e.getMessage());
					return false;
				}
			}
		}
		log.warn("Driver {0} could not be updated",username);
		return false;
	}
}
