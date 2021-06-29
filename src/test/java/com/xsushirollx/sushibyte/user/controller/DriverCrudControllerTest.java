package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.xsushirollx.sushibyte.user.dto.DriverDTO;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.security.JWTUtil;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr
 * Controller test for driver crud operations accessible to driver
 */
@SpringBootTest
@AutoConfigureMockMvc
class DriverCrudControllerTest {
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
	void driverReadTest_on_success() throws Exception {
		DriverDTO user = new DriverDTO();
		// read a valid account
		when(userService.readDriver(Mockito.anyString())).thenReturn(user);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 3)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(get("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isOk());
	}
	
	@Test
	void driverReadTest_on_fail() throws Exception {
		// read an invalid account
		when(userService.readDriver(Mockito.anyString())).thenReturn(null);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 3)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(get("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNotFound());
	}
	
	@Test
	void driverRectivateTest_on_success() throws Exception {
		// reactivate a valid account
		when(userService.reactivateDriver(Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 3)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(post("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isCreated());
	}
	
	@Test
	void driverReactivateTest_on_fail() throws Exception {
		// reactivate an invalid account
		when(userService.reactivateDriver(Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 3)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(post("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNotModified());
	}
	
	@Test
	void driverDeactivateTest_on_success() throws Exception {
		// deactivate an invalid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 3)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNoContent());
	}
	
	@Test
	void driverDeactivateTest_on_fail() throws Exception {
		// deactivate an invalid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));
		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/users/driver/96").accept(MediaType.APPLICATION_JSON).header("Authorization", token)).andExpect(status().isNotModified());
	}

}
