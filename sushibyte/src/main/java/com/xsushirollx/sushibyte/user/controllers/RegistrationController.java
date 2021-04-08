package com.xsushirollx.sushibyte.user.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr
 * Controller for user registration and verification
 */
@RestController
public class RegistrationController {
	@Autowired
	UserService u1;
	static Logger log = LogManager.getLogger(RegistrationController.class.getName());

	/**
	 * Temporary test method to be deleted upon creation of a node js server
	 * @param request
	 * @return verification code
	 */
	@PostMapping("/helloworld2")
	public String helloWorld2(HttpServletRequest request) {
		return "Test Mapping: Your verification code is " + request.getAttribute("verification_code");
	}

	/**
	 * https://www.codejava.net/frameworks/spring-boot/email-verification-example
	 * Verifies user from code
	 * @param code
	 * @return
	 */
	@GetMapping("/verify")
	public String verifyUser(HttpServletResponse response, @Param("code") String code) {
		if (u1.verifyUserEmail(code)) {
			return "verify_success";
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "verify_fail";
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param email
	 * @return updates verification email
	 */
	@PostMapping("/verify")
	public String resendVerificationCode(HttpServletRequest request, HttpServletResponse response, @Param("email") String email) {
		String code = u1.resetVerificationCode(email);
		if (code!=null) {
			try {
				request.setAttribute("verification_code", code);
				request.getRequestDispatcher("/helloworld2").forward(request, response);
			} catch (ServletException e) {
				log.log(Level.WARN,e.getMessage());
			} catch (IOException e) {
				log.log(Level.WARN,e.getMessage());
			}
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "resend_fail";
		}
		return "resend_fail";
	}

	/**
	 * Request user service to generate a user based on given fields and store in database
	 * @param request
	 * @param response
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param phone
	 * @param username
	 * @return redirect if successful
	 */
	@PostMapping("/user")
	public String registerUser(HttpServletRequest request, HttpServletResponse response,
			@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
			@Param("password") String password, @Param("phone") String phone, @Param("username") String username) {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(email);
		userDTO.setFirstName(firstName);
		userDTO.setLastName(lastName);
		userDTO.setPassword(password);
		userDTO.setUsername(username);
		userDTO.setPhone(phone);
		String verificationCode = u1.registerOnValidation(userDTO);
		if (verificationCode != null) {
			request.setAttribute("verification_code", verificationCode);
			try {
				request.getRequestDispatcher("/helloworld2").forward(request, response);
			} catch (ServletException e) {
				log.log(Level.WARN,e.getMessage());
			} catch (IOException e) {
				log.log(Level.WARN,e.getMessage());
			}
		} else {
			// could check each validation step
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return "registration_fail";
		}
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return "registration partially completed";
	}
}
