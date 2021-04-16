package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
	
	@Test
	void updateTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void updateTestOnFail() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	void readTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void readTestOnFail() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	void deleteTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void deleteTestOnFail() throws Exception {
		// verify a valid account
		when(userService.updateAccount(1,null)).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

}
