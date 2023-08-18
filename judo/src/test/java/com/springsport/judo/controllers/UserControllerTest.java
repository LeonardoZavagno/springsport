package com.springsport.judo.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.springsport.judo.models.User;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserController controller;

	@Test
	public void controllerShouldReturnAllUsers() throws Exception {
        User user1 = new User(1L, "name_1", "surname_1");
        User user2 = new User(2L, "name_2", "surname_2");

		when(controller.list()).thenReturn(List.of(user1, user2));
		this.mockMvc.perform(get("/api/v1/users"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].user_id").value(user1.getUser_id()))
            .andExpect(jsonPath("$[1].user_id").value(user2.getUser_id()))
            .andExpect(jsonPath("$[0].user_name").value(user1.getUser_name()))
            .andExpect(jsonPath("$[1].user_name").value(user2.getUser_name()))
            .andExpect(jsonPath("$[0].user_surname").value(user1.getUser_surname()))
            .andExpect(jsonPath("$[1].user_surname").value(user2.getUser_surname()));
	}

	@Test
	public void controllerShouldReturnOneUser() throws Exception {
        User user1 = new User(1L, "name_1", "surname_1");

		when(controller.get(1L)).thenReturn(user1);
		this.mockMvc.perform(get("/api/v1/users/1"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.user_id").value(user1.getUser_id()))
            .andExpect(jsonPath("$.user_name").value(user1.getUser_name()))
            .andExpect(jsonPath("$.user_surname").value(user1.getUser_surname()));
	}
}
