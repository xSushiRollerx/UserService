package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.xsushirollx.sushibyte.user.dto.UserDTO;
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
	
	@Test
	void updateTestOnSuccess() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.updateAccount(Mockito.anyInt(),Mockito.any(UserDTO.class))).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNoContent());
		
	}

	@Test
	void updateTestOnFail() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.updateAccount(Mockito.anyInt(),Mockito.any(UserDTO.class))).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(put("/users/user/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNotModified());
	}
	
	@Test
	void readTestOnSuccess() throws Exception {
		UserDTO user = new UserDTO();
		// verify a valid account
		when(userService.getUserInfo(Mockito.anyInt())).thenReturn(user);
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void readTestOnFail() throws Exception {
		// verify a valid account
		when(userService.getUserInfo(Mockito.anyInt())).thenReturn(null);
		// temporary for testing purposes
		mockMvc.perform(get("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	@Test
	void deleteTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.closeAccount(Mockito.anyInt())).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	void deleteTestOnFail() throws Exception {
		// verify a valid account
		when(userService.closeAccount(Mockito.anyInt())).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(delete("/users/user/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotModified());
	}

}
