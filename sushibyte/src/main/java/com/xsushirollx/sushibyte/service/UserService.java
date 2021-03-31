package com.xsushirollx.sushibyte.service;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xsushirollx.sushibyte.entities.Customer;
import com.xsushirollx.sushibyte.entities.User;
import com.xsushirollx.sushibyte.repositories.CustomerDAO;
import com.xsushirollx.sushibyte.repositories.UserDAO;
import com.xsushirollx.sushibyte.utils.PasswordUtils;

@Service
public class UserService {
	@Autowired
	UserDAO u1;
	@Autowired
	CustomerDAO c1;
	static Logger log = LogManager.getLogger(UserService.class.getName());
	
	/**
	 * @param user
	 * @return true if user has successfully been saved
	 */
	@Transactional
	public boolean registerOnValidation(User user) {
		if(!validatePassword(user.getPassword())) {
			return false;
		}
		if(!validateName(user.getFirstName())) {
			return false;
		}
		if(!validateName(user.getLastName())) {
			return false;
		}
		if(!validateEmail(user.getEmail())) {
			return false;
		}
		if(!validateUsername(user.getUsername())) {
			return false;
		}
		if(!validatePhone(user.getPhone())) {
			return false;
		}
		String salt = PasswordUtils.getSalt(30);
		user.setPassword(PasswordUtils.generateSecurePassword(user.getPassword(), salt));
		user.setSalt(salt);
		try {
			//email validated with hibernate
			User user1 = u1.save(user);
			c1.save(new Customer(user1.getId()));
		}
		catch(Exception e) {
			log.debug("Was unable to save user.");
		}
		return true;
	}

	public boolean validateEmail(String email) {
		String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
		Pattern pattern = Pattern.compile(regex);
		if(pattern.matcher(email).matches()) {
			return (u1.findByEmail(email)==null)?true:false;
		}
		return false;
	}

	public boolean validateUsername(String username) {
		if(username==null) {
			return false;
		}
		return (u1.findByUsername(username)==null)?true:false;
	}
	
	public boolean validatePhone(String phone) {
		if(phone==null||phone.length()!=10) {
			return false;
		}
		for(char i:phone.toCharArray()) {
			if(!Character.isDigit(i))
				return false;
		}
		return (u1.findByPhone(phone)==null)?true:false;
	}

	public boolean validateName(String name) {
		Pattern pattern = Pattern.compile("[0-9]");
		if(name==null||pattern.matcher(name).find()) {
			return false;
		}
		return true;
	}

	public boolean validatePassword(String password) {
		if (password==null||password.length()<6||password.length()>20) {
			return false;
		}
		return true;
	}
}
