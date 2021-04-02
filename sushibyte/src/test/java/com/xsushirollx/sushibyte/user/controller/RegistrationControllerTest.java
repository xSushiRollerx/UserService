package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.servlet.http.HttpServletResponse;
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
class RegistrationControllerTest {
	@MockBean
	UserService userService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void registrationTestOnSuccess() throws Exception {
		// let all these null values be valid, it returns a verification code
		when(userService.registerOnValidation(null, null, null, null, null, null))
				.thenReturn("21839y23823127heubs2");
		// temporary for testing purposes
		mockMvc.perform(post("/users/register").accept(MediaType.APPLICATION_JSON))
				.andExpect(forwardedUrl("/users/helloworld2"));
	}

	@Test
	void registrationTestOnFail() throws Exception {
		// user not registered
		when(userService.registerOnValidation(null, null, null, null, null, null))
				.thenReturn(null);
		// temporary for testing purposes
		mockMvc.perform(post("/users/register").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(HttpServletResponse.SC_NOT_ACCEPTABLE));
	}

	@Test
	void verificationTestOnSuccess() throws Exception {
		// verify a valid account
		when(userService.verifyUserEmail(null)).thenReturn(true);
		// temporary for testing purposes
		mockMvc.perform(put("/users/verify").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void verificationTestOnFail() throws Exception {
		// verify a valid account
		when(userService.verifyUserEmail(null)).thenReturn(false);
		// temporary for testing purposes
		mockMvc.perform(put("/users/verify").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

}
