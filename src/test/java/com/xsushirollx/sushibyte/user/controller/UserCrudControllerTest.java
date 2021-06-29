package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.user.dao.UserDAO;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.security.JWTUtil;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr
 * Controller test for user registration and verification
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserCrudControllerTest {
	@MockBean
	UserService userService;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JWTUtil util;
	@MockBean
	UserDAO udao;
	
	@Test
	void updateTestOnSuccess() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.updateAccount(Mockito.anyString(),Mockito.any(UserDTO.class))).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNoContent());
		
	}

	@Test
	void updateTestOnFail() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.updateAccount(Mockito.anyString(),Mockito.any(UserDTO.class))).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNotModified());
	}
	
	@Test
	void readTestOnSuccess() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.getUserInfo(Mockito.anyString())).thenReturn(user);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isOk());
	}

	@Test
	void readTestOnFail() throws Exception {
		// verify a valid account
		when(userService.getUserInfo(Mockito.anyString())).thenReturn(null);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNotFound());
	}
	
	@Test
	void deleteTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.closeAccount(Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNoContent());
	}

	@Test
	void deleteTestOnFail() throws Exception {
		// verify a valid account
		when(userService.closeAccount(Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNotModified());
	}

}
