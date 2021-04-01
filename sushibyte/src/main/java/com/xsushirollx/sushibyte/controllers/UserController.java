package com.xsushirollx.sushibyte.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.entities.User;
import com.xsushirollx.sushibyte.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService u1;
	
	@GetMapping("/helloworld")
	public String helloWorld() {
		return "Hello World";
	}
	
	/**
	 * https://www.codejava.net/frameworks/spring-boot/email-verification-example
	 * @param code
	 * @return
	 */
	@GetMapping("/verify")
	public String verifyUser(@Param("code") String code) {
	    if (u1.verifyUserEmail(code)) {
	        return "verify_success";
	    } else {
	        return "verify_fail";
	    }
	}
	
	@PostMapping("/register")
	public String registerUser(HttpServletRequest request,
			@Param("firstName") String firstName,
			@Param("lastName") String lastName,
			@Param("email") String email,
			@Param("password") String password,
			@Param("phone") String phone,
			@Param("username") String username) {
	    User user = new User(firstName,lastName, phone, email, username, password);
	    if (u1.registerOnValidation(user, request.getRequestURL().toString())) {
	        return "registration_success";
	    } else {
	    	//could check each validation step
	        return "registration_fail";
	    }
	}
}
