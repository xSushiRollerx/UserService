package com.xsushirollx.sushibyte.user.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
@RequestMapping("/auth")
public class AuthorizationController {
	@Autowired
	UserService u1;
	
	static Logger log = LogManager.getLogger(RegistrationController.class.getName());

	/**
	 * @param code
	 * @return
	 * @throws IOException 
	 */
	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, @Param("id") String id,
			@Param("password") String password) throws IOException {
		Integer key = u1.logIn(id, password);
		if (key!=null) {
			HttpSession session = request.getSession();
			session.setAttribute("key", key);
			response.sendRedirect("/index");	//Temporary redirect location for now
			return "login_success";
		} else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "login_fail";
		}
	}
	
	@GetMapping("/logout")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Integer key = (Integer)session.getAttribute("key");
		session.invalidate();
		u1.logOut(key);
		response.sendRedirect("/login");
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
