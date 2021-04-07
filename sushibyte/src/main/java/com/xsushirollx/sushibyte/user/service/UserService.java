package com.xsushirollx.sushibyte.user.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsushirollx.sushibyte.user.dto.LoggedUser;
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
	//users mapped with user role
	Map<Integer,LoggedUser> loggedUsers = new HashMap<Integer,LoggedUser>();
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
				checkEmailExist(user.getEmail())||
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
			User user1 = user;
			c1.save(new Customer(user.getId()));
			Thread t = new Thread(()->{
				LocalDate date = LocalDate.now();
				//Thread.sleep(10000);
				while(date==LocalDate.now()) {
					//yield because wait requires synchronous block
					//may opt for a wait and have a separate daily function
					//notify all waiting threads
					Thread.yield();
				}
				final User user2 = u1.findByUsername(username);
				if (!user1.isActive()) {
					deleteUserPermanent(user2.getId());
				}
			});
			t.start();
		} catch (Exception e) {
		}
		return user.getVerificationCode();
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
		return false;
	}
	
	/**
	 * @param email
	 * @return true if email exists in database
	 */
	private boolean checkEmailExist(String email) {
		return(u1.findByEmail(email) != null)?true:false;
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
	 * @return true if email still exists and is currently not active
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
	 * @return true if user is deleted or not exist
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
	
	/**
	 * @param id
	 * @param password
	 * @return key to access credential information
	 */
	public Integer logIn(String id, String password) {
		User user = null;
		try {
			if(validateEmail(id)) {
				user=u1.findByEmail(id);
			}
			else {
				user=u1.findByUsername(id);
			}
		}
		catch(Exception e2) {
			log.log(Level.WARN, "Cannot find user");
			return null;
		}
		if (PasswordUtils.verifyUserPassword(password,user.getPassword(),user.getSalt())) {
			LoggedUser cred = new LoggedUser(user.getUsername(),user.getId());
			loggedUsers.put(cred.getHashCode(), cred);
			return cred.getHashCode();
		}
		log.log(Level.INFO, password + " did not match the one on file");
		return null;
	}
	
	/**
	 * @param key not the id of the record, but key of the hashmap credentials
	 * @return
	 */
	public boolean logOut(Integer key) {
		if (loggedUsers.containsKey(key)) {
			loggedUsers.remove(key);
			return true;
		}
		return false;
	}
	
	/**
	 * @param key
	 * @return role id of user if it exists,
	 */
	public Integer getAuthorization(Integer key) {
		LoggedUser cred = loggedUsers.get(key); 
		if (cred!=null) {
			return cred.getUserRole();
		}
		return null;
	}
}
