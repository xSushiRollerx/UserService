package com.xsushirollx.sushibyte.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.user.dto.DriverDTO;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr More to be added Just including method to read all users
 */
@RestController
@RequestMapping("/admins")
public class AdminController {
	@Autowired
	UserService userService;

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> deactivateUser(@PathVariable("userId") String userId) {
		if (userService.closeAccount(userId)) {
			return new ResponseEntity<String>("Delete_successful", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Delete_failed", HttpStatus.NOT_MODIFIED);
	}
	
	@DeleteMapping("/driver/{userId}")
	public ResponseEntity<String> deactivateDriver(@PathVariable("userId") String userId) {
		if (userService.deactivateDriver(userId)) {
			return new ResponseEntity<String>("Delete_successful", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Delete_failed", HttpStatus.NOT_MODIFIED);
	}

	@PutMapping("/user/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
		if (userService.updateAccount(userId, userDTO)) {
			return new ResponseEntity<String>("Update_successful", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Update_failed", HttpStatus.NOT_MODIFIED);
	}

	@PutMapping("/user/{userId}/role")
	public ResponseEntity<String> updateUserRole(@PathVariable("userId") String userId, @RequestBody Integer roleId) {
		if (userService.updateAccountRole(userId, roleId)) {
			return new ResponseEntity<String>("Update_successful", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Update_failed", HttpStatus.NOT_MODIFIED);
	}

	@PutMapping("/driver/{driverId}")
	public ResponseEntity<String> updateDriver(@PathVariable("driverId") String driverId,
			@RequestBody DriverDTO driver) {
		if (userService.updateDriver(driver, driverId)) {
			return new ResponseEntity<String>("Driver updated", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Driver not updated", HttpStatus.NOT_MODIFIED);
	}

}
