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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr
 * Controller for user registration and verification
 */
@RestController
@RequestMapping("/users")
public class RegistrationController {
	@Autowired
	UserService u1;
	static Logger log = LogManager.getLogger(RegistrationController.class.getName());

	/**
	 * Temporary test method to be deleted upon creation of a node js server
	 * @param request
	 * @return verification code
	 */
	@GetMapping("/helloworld2")
	public String helloWorld2(HttpServletRequest request) {
		return "Hello World. Your code is " + request.getAttribute("verification_code");
	}

	/**
	 * https://www.codejava.net/frameworks/spring-boot/email-verification-example
	 * Verifies user from code
	 * @param code
	 * @return
	 */
	@PutMapping("/verify")
	public String verifyUser(HttpServletResponse response, @Param("code") String code) {
		if (u1.verifyUserEmail(code)) {
			return "verify_success";
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "verify_fail";
		}
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
	@PostMapping("/register")
	public String registerUser(HttpServletRequest request, HttpServletResponse response,
			@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email,
			@Param("password") String password, @Param("phone") String phone, @Param("username") String username) {
		String verificationCode = u1.registerOnValidation(firstName, lastName, phone, email, username, password);
		if (verificationCode != null) {
			request.setAttribute("verification_code", verificationCode);
			try {
				request.getRequestDispatcher("/users/helloworld2").forward(request, response);
			} catch (ServletException e) {
				log.log(Level.WARN,e.getMessage());
			} catch (IOException e) {
				log.log(Level.WARN,e.getMessage());
			}
			;
		} else {
			// could check each validation step
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return "registration_fail";
		}
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return "registration partially completed";
	}
}
