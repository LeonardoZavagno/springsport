package com.springsport.core.controllers.rest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HomeRestController.class)
public class HomeRestControllerTest {

    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRestController controller;

    @Value("${app.version}")
    private String appVersion;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void controllerShouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/"))
            .andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.app-version").value(appVersion));
	}
}
