package com.xsushirollx.sushibyte.user.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.xsushirollx.sushibyte.user.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationControllerTest {
	@MockBean
	UserService userService;
	@Autowired
	MockMvc mockMvc;
	@Autowired
    MockHttpSession session;

	@Test
	void logInTest() throws Exception {
		when(userService.logIn(null, null)).thenReturn(null);
		mockMvc.perform(get("/auth/login").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().is(HttpServletResponse.SC_NOT_FOUND));
		when(userService.logIn(null, null)).thenReturn(123);
		mockMvc.perform(get("/auth/login").accept(MediaType.APPLICATION_JSON))
		.andExpect(redirectedUrl("/index"));
	}
	
	@Test
	void LogOutTest() throws Exception {
		when(userService.logOut(null)).thenReturn(true);
		mockMvc.perform(get("/auth/logout").accept(MediaType.APPLICATION_JSON))
		.andExpect(redirectedUrl("/auth/login"));
	}

}
