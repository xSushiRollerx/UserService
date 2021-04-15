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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
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
	 * https://www.codejava.net/frameworks/spring-boot/email-verification-example
	 * Verifies user from code
	 * @param code
	 * @return
	 */
	@GetMapping("/verify")
	public ResponseEntity<String> verifyUser(HttpServletResponse response, @Param("code") String code) {
		if (u1.verifyUserEmail(code)) {
			return new ResponseEntity<String>("verify_success", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("verify_fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param email
	 * @return updates verification email
	 */
	@PutMapping("/verify")
	public ResponseEntity<String> resendVerificationCode(@RequestBody String email) {
		String code = u1.resetVerificationCode(email);
		if (code!=null) {
			HttpHeaders header = new HttpHeaders();
			header.add("location", "http://localhost:8080/mail");
			return new ResponseEntity<String>(code, header, HttpStatus.valueOf(301));
		} else {
			return new ResponseEntity<String>("resend_fail", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Request user service to generate a user based on given fields and store in database
	 * @return redirect if successful
	 */
	@PostMapping("/user")
	public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
		String verificationCode = u1.registerOnValidation(userDTO);
		if (verificationCode != null) {
			HttpHeaders header = new HttpHeaders();
			header.add("location", "http://localhost:8080/mail");
			return new ResponseEntity<String>(verificationCode, header, HttpStatus.valueOf(301));
		} else {
			return new ResponseEntity<String>("registration_fail", HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
