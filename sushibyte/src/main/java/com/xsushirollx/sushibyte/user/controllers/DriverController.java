package com.xsushirollx.sushibyte.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xsushirollx.sushibyte.user.dto.DriverDTO;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr
 * Users cannot become drivers or update without admin assistance
 * They can reactivate existing accounts though
 */
@RestController
@RequestMapping("/users")
public class DriverController {
	@Autowired
	UserService userService;
	
	@PreAuthorize(value = "hasAuthority('DRIVER') and (#driverId == (principal.id).toString()) ")
	@PostMapping("/driver/{driverId}")
	public ResponseEntity<String> reactivateDriver(@PathVariable("driverId") String driverId){
		if (userService.reactivateDriver(driverId)) {
			return new ResponseEntity<String>("Driver reactivated",HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("Driver not reactivated",HttpStatus.NOT_MODIFIED);
	}
	
	@PreAuthorize(value = "hasAuthority('DRIVER') and (#driverId == (principal.id).toString()) ")
	@DeleteMapping("/driver/{driverId}")
	public ResponseEntity<String> deactivateDriver(@PathVariable("driverId") String driverId){
		if (userService.deactivateDriver(driverId)) {
			return new ResponseEntity<String>("Driver deleted",HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<String>("Driver not deleted",HttpStatus.NOT_MODIFIED);
	}
	
	@PreAuthorize(value = "hasAuthority('DRIVER') and (#driverId == (principal.id).toString()) ")
	@GetMapping("/driver/{driverId}")
	public ResponseEntity<DriverDTO> readDriverInfo(@PathVariable("driverId") String driverId){
		DriverDTO driver = userService.readDriver(driverId);
		if (driver!=null) {
			return new ResponseEntity<DriverDTO>(driver, HttpStatus.OK);
		}
		return new ResponseEntity<DriverDTO>(HttpStatus.NOT_FOUND);
	}
}
