package com.xsushirollx.sushibyte.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.user.dto.DriverDTO;
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
	
	@Test
	void driverReadTest_on_success() throws Exception {
		DriverDTO user = new DriverDTO();
		// read a valid account
		when(userService.readDriver(Mockito.anyString())).thenReturn(user);
		mockMvc.perform(get("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void driverReadTest_on_fail() throws Exception {
		// read an invalid account
		when(userService.readDriver(Mockito.anyString())).thenReturn(null);
		mockMvc.perform(get("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	void driverRectivateTest_on_success() throws Exception {
		// reactivate a valid account
		when(userService.reactivateDriver(Mockito.anyString())).thenReturn(true);
		mockMvc.perform(post("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}
	
	@Test
	void driverReactivateTest_on_fail() throws Exception {
		// reactivate an invalid account
		when(userService.reactivateDriver(Mockito.anyString())).thenReturn(false);
		mockMvc.perform(post("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotModified());
	}
	
	@Test
	void driverDeactivateTest_on_success() throws Exception {
		// deactivate an invalid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(true);
		mockMvc.perform(delete("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}
	
	@Test
	void driverDeactivateTest_on_fail() throws Exception {
		// deactivate an invalid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(false);
		mockMvc.perform(delete("/users/driver/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotModified());
	}

}
