package com.xsushirollx.sushibyte.user.service;

import java.time.LocalDate;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xsushirollx.sushibyte.user.entities.Customer;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.repositories.CustomerDAO;
import com.xsushirollx.sushibyte.user.repositories.DriverDAO;
import com.xsushirollx.sushibyte.user.repositories.UserDAO;
import com.xsushirollx.sushibyte.user.utils.PasswordUtils;

/**
 * @author dyltr 
 * references: https://www.codejava.net/frameworks/spring-boot/email-verification-example
 * Provide business logic for general users        
 */
@Service
public class UserService {
	@Autowired
	private UserDAO u1;
	@Autowired
	private CustomerDAO c1;
	@Autowired
	private DriverDAO d1;
	static Logger log = LogManager.getLogger(UserService.class.getName());

	/**
	 * @param phone 
	 * @param email 
	 * @param username 
	 * @param password 
	 * @param string 
	 * @param user
	 * @return true if user has successfully been saved
	 */
	@Transactional
	public String registerOnValidation(String firstName, String lastName, String phone, String email, String username, String password) {
		User user = new User(firstName,lastName,phone,email,username,password);
		if (!validatePassword(user.getPassword())||
				!validateName(user.getFirstName())||
				!validateName(user.getLastName())||
				!validateEmail(user.getEmail())||
				!validateUsername(user.getUsername())||
				!validatePhone(user.getPhone())) {
			return null;
		}
		String salt = PasswordUtils.getSalt(30);
		user.setPassword(PasswordUtils.generateSecurePassword(user.getPassword(), salt));
		user.setSalt(salt);
		try {
			// email validated with hibernate
			user = u1.save(user);
			final User user1 = user;
			c1.save(new Customer(user.getId()));
			Thread t = new Thread(()->{
				LocalDate date = LocalDate.now();
				//Thread.sleep(10000);
				while(date==LocalDate.now()) {
					//yield because wait requires synchronous block
					Thread.yield();
				}
				deleteUserPermanent(user1.getId());
			});
			t.start();
		} catch (Exception e) {
		}
		return user.getVerificationCode();
	}

	/**
	 * @param email
	 * @return true if meets criteria and is unique
	 */
	public boolean validateEmail(String email) {
		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(email).matches()) {
			return (u1.findByEmail(email) == null) ? true : false;
		}
		return false;
	}

	/**
	 * @param username
	 * @return true if meets criteria and is unique
	 */
	public boolean validateUsername(String username) {
		if (username == null) {
			return false;
		}
		return (u1.findByUsername(username) == null) ? true : false;
	}

	/**
	 * @param phone
	 * @return true if meets criteria and is unique
	 */
	public boolean validatePhone(String phone) {
		if (phone == null || phone.length() != 10) {
			return false;
		}
		for (char i : phone.toCharArray()) {
			if (!Character.isDigit(i))
				return false;
		}
		return (u1.findByPhone(phone) == null) ? true : false;
	}

	/**
	 * @param name
	 * @return true if meets criteria
	 */
	public boolean validateName(String name) {
		Pattern pattern = Pattern.compile("[0-9]");
		if (name == null || pattern.matcher(name).find()) {
			return false;
		}
		return true;
	}

	/**
	 * @param password
	 * @return true if meets criteria
	 */
	public boolean validatePassword(String password) {
		if (password == null || password.length() < 6 || password.length() > 20) {
			return false;
		}
		return true;
	}

	/**
	 * removes verification code and set active
	 * @param verificationCode
	 * @return
	 */
	public boolean verifyUserEmail(String verificationCode) {
		User user = u1.findByVericationCode(verificationCode);
		if (user == null||user.isActive()) {
			return false;
		}
		user.setActive(true);
		user.setVerificationCode(null);
		u1.save(user);
		return true;
	}

	/**
	 * Only used for admin or unsuccessful email verification
	 * @param user
	 * @return
	 */
	@Transactional
	private boolean deleteUserPermanent(int id) {
		try {
			if (d1.existsById(id)) {
				d1.deleteById(id);
			}
			if (c1.existsById(id)) {
				c1.deleteById(id);
			}
			if (u1.existsById(id)) {
				u1.deleteById(id);
			}
		} catch (Exception e) {
			log.log(Level.INFO, "User not deleted");
			return false;
		}
		log.log(Level.INFO, "User deleted");
		return true;
	}
}
