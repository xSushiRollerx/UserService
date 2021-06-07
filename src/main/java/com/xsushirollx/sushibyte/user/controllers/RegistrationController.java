package com.xsushirollx.sushibyte.user.controllers;

import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xsushirollx.sushibyte.user.dto.MailDTO;
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
	@PutMapping(value = "/verify")
	public ResponseEntity<MailDTO> resendVerificationCode(@RequestBody String email) {
		log.warn(email);
		String code = u1.resetVerificationCode(email);
		if (code!=null) {
			MailDTO mail = new MailDTO();
			mail.setBody(code);
			mail.setEmail(email);
			return new ResponseEntity<MailDTO>(mail, HttpStatus.OK);
		} else {
			return new ResponseEntity<MailDTO>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Request user service to generate a user based on given fields and store in database
	 * @return redirect if successful
	 */
	@PostMapping("/user")
	public ResponseEntity<MailDTO> registerUser(@RequestBody UserDTO userDTO) {
		String code = u1.registerOnValidation(userDTO);
		if (code != null) {
			MailDTO mail = new MailDTO();
			mail.setBody(code);
			mail.setEmail(userDTO.getEmail());
			return new ResponseEntity<MailDTO>(mail, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<MailDTO>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
