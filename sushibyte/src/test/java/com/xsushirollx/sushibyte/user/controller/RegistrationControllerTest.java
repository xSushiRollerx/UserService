package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpServletResponse;
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
class RegistrationControllerTest {
	@MockBean
	UserService userService;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	void registrationTestOnSuccess() throws Exception {
		UserDTO user = new UserDTO();
		// let all these null values be valid, it returns a verification code
		when(userService.registerOnValidation(Mockito.any(UserDTO.class)))
				.thenReturn("21839y23823127heubs2");
		mockMvc.perform(post("/users/user").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isCreated());
	}

	@Test
	void registrationTestOnFail() throws Exception {
		UserDTO user = new UserDTO();
		// user not registered
		when(userService.registerOnValidation(Mockito.any(UserDTO.class)))
				.thenReturn(null);
		mockMvc.perform(post("/users/user").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().is(HttpServletResponse.SC_NOT_ACCEPTABLE));
	}

	@Test
	void resendLinkTestOnSuccess() throws Exception {
		String email = null;
		when(userService.resetVerificationCode("null")).thenReturn("code");
		// temporary for testing purposes
		mockMvc.perform(put("/users/verify").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(email)))
		.andExpect(status().is(204));
	}
	
	@Test
	void resendLinkTestOnFail() throws Exception {
		// user not registered
		String email = null;
		when(userService.resetVerificationCode("null")).thenReturn(null);
		// temporary for testing purposes
		mockMvc.perform(put("/users/verify").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(email)))
		.andExpect(status().isNotFound());
	}
	
	@Test
	void verificationTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.verifyUserEmail(null)).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(get("/users/verify").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void verificationTestOnFail() throws Exception {
		// verify a valid account
		when(userService.verifyUserEmail(null)).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(get("/users/verify").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotAcceptable());
	}

}
