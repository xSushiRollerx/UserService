package com.xsushirollx.sushibyte.user.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserCrudController {
	@Autowired
	UserService userService;
	static Logger log = LogManager.getLogger(UserCrudController.class.getName());
	
	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR') or (#userId == principal.id.toString()) ")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deactivateUser(@PathVariable("userId") String userId){
		log.warn(userId);
		if(userService.closeAccount(userId)) {
			return new ResponseEntity<String>("Delete_successful",HttpStatus.valueOf(204));
		}
		return new ResponseEntity<String>("Delete_failed",HttpStatus.NOT_MODIFIED);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR') or (#userId == principal.id.toString()) ")
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDTO> readUser(@PathVariable("userId") String userId){
		UserDTO user = userService.getUserInfo(userId);
		if (user==null) {
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@PreAuthorize(value = "hasAuthority('ADMINISTRATOR') or (#userId == principal.id.toString()) ")
	@PutMapping("/user/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable("userId") String userId, 
			@RequestBody UserDTO userDTO){
		if(userService.updateAccount(userId, userDTO)) {
			return new ResponseEntity<String>("Update_successful",HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Update_failed",HttpStatus.NOT_MODIFIED);
	}
}
