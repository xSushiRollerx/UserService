package com.xsushirollx.sushibyte.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.access.AccessDeniedException;

@ControllerAdvice
public class UserExceptionHandler {


	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> accessDeniedException(Exception e){
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("403: Access Denied");
	}
	
	public ResponseEntity<String> handleDefaultException(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("500: Internal Server Error");
	}
	
}
