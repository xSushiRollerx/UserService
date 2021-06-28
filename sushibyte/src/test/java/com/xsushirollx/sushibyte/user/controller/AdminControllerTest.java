package com.xsushirollx.sushibyte.user.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.user.dao.UserDAO;
import com.xsushirollx.sushibyte.user.dto.DriverDTO;
import com.xsushirollx.sushibyte.user.dto.UserDTO;
import com.xsushirollx.sushibyte.user.entities.User;
import com.xsushirollx.sushibyte.user.security.JWTUtil;
import com.xsushirollx.sushibyte.user.service.UserService;

/**
 * @author dyltr Controller test for crud operations accessible to admin
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
	@MockBean
	UserService userService;
	@MockBean
	UserDAO udao;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	JWTUtil util;

	@Test
	void roleUpdateTest_on_success() throws JsonProcessingException, Exception {
		// change role of valid account
		when(userService.updateAccountRole(Mockito.anyString(), Mockito.anyInt())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/user/96/role").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token).content(objectMapper.writeValueAsString(new Integer(3))))
				.andExpect(status().isNoContent());
	}

	@Test
	void roleUpdate_on_fail() throws JsonProcessingException, Exception {
		// change role of valid account
		when(userService.updateAccountRole(Mockito.anyString(), Mockito.anyInt())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/user/1/role").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(new Integer(3)))).andExpect(status().isNotModified());
	}

	@Test
	void updateDriverTest_on_success() throws JsonProcessingException, Exception {
		DriverDTO user = new DriverDTO();
		// update an invalid account
		when(userService.updateDriver(Mockito.any(DriverDTO.class),Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/driver/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNoContent());
	}
	
	@Test
	void updateDriverTest_on_fail() throws JsonProcessingException, Exception {
		DriverDTO user = new DriverDTO();
		// update an invalid account
		when(userService.updateDriver(Mockito.any(DriverDTO.class),Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/driver/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNotModified());
	}
	
	@Test
	void userDeactivateTest_on_success() throws Exception {
		// delete a valid account
		when(userService.closeAccount(Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/admins/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNoContent());
	}
	
	@Test
	void userDeactivateTest_on_fail() throws Exception {
		// delete a valid account
		when(userService.closeAccount(Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/admins/user/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotModified());
	}
	
	@Test
	void driverDeactivateTest_on_success() throws Exception {
		// delete a valid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/admins/driver/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNoContent());
	}
	
	@Test
	void driverDeactivateTest_on_fail() throws Exception {
		// delete a valid account
		when(userService.deactivateDriver(Mockito.anyString())).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(delete("/admins/driver/1").accept(MediaType.APPLICATION_JSON).header("Authorization", token))
		.andExpect(status().isNotModified());
	}
	
	@Test
	void userUpdateTest_on_success() throws JsonProcessingException, Exception {
		UserDTO user = new UserDTO();
		// update a valid account
		when(userService.updateAccount(Mockito.anyString(),Mockito.any(UserDTO.class))).thenReturn(true);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/user/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNoContent());
	}
	
	@Test
	void userUpdateTest_on_fail() throws JsonProcessingException, Exception {
		UserDTO user = new UserDTO();
		// update an invalid account
		when(userService.updateAccount(Mockito.anyString(),Mockito.any(UserDTO.class))).thenReturn(false);
		when(udao.findById(Mockito.anyInt())).thenReturn(Optional.of(new User(96, 2)));

		String token = "Bearer " + util.generateToken("96");
		mockMvc.perform(put("/admins/user/1").contentType(MediaType.APPLICATION_JSON).header("Authorization", token)
				.content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isNotModified());
	}
	
}
