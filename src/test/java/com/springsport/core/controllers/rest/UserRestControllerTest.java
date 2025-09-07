package com.springsport.core.controllers.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsport.core.models.User;
import com.springsport.core.services.UserService;

@WebMvcTest(UserRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserRestControllerTest {

    @Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test
	@DisplayName("GET /api/v1/users - Should return all users and 200 OK")
	public void shouldReturnAllUsers() throws Exception {
        User user1 = new User(1L, "name_1", "surname_1");
        User user2 = new User(2L, "name_2", "surname_2");

		when(userService.list()).thenReturn(List.of(user1, user2));
		this.mockMvc.perform(get("/api/v1/users").with(user("leonardo").roles("ADMIN")))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$[0].user_id").value(user1.getUser_id()))
            .andExpect(jsonPath("$[1].user_id").value(user2.getUser_id()))
            .andExpect(jsonPath("$[0].user_name").value(user1.getUser_name()))
            .andExpect(jsonPath("$[1].user_name").value(user2.getUser_name()))
            .andExpect(jsonPath("$[0].user_surname").value(user1.getUser_surname()))
            .andExpect(jsonPath("$[1].user_surname").value(user2.getUser_surname()));
	}

	@Test
	@DisplayName("GET /api/v1/users/{id} - Should return a single user by ID and 200 OK")
	public void shouldReturnOneUser() throws Exception {
        User user1 = new User(1L, "name_1", "surname_1");

		when(userService.get(1L)).thenReturn(user1);
		this.mockMvc.perform(get("/api/v1/users/1").with(user("leonardo").roles("ADMIN")))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.user_id").value(user1.getUser_id()))
            .andExpect(jsonPath("$.user_name").value(user1.getUser_name()))
            .andExpect(jsonPath("$.user_surname").value(user1.getUser_surname()));
	}

	@Test
    @DisplayName("POST /api/v1/users - Should create a new user and return 201 Created")
    public void shouldCreateUserAndReturn201() throws Exception {
        User userToCreate = new User(null, "new_name", "new_surname");
        User createdUser = new User(3L, "new_name", "new_surname");

        when(userService.create(any(User.class))).thenReturn(createdUser);

        mockMvc.perform(post("/api/v1/users")
                .with(user("leonardo").roles("ADMIN")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToCreate)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/users/3"))
                .andExpect(jsonPath("$.user_id").value(createdUser.getUser_id()))
                .andExpect(jsonPath("$.user_name").value(createdUser.getUser_name()))
                .andExpect(jsonPath("$.user_surname").value(createdUser.getUser_surname()));
    }

    @Test
    @DisplayName("PUT /api/v1/users/{id} - Should update an existing user and return 200 OK")
    public void shouldUpdateUser() throws Exception {
        Long userId = 1L;
        User userToUpdate = new User(userId, "updated_name", "updated_surname");

        when(userService.update(eq(userId), any(User.class))).thenReturn(userToUpdate);

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                .with(user("leonardo").roles("ADMIN")).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userToUpdate)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user_id").value(userToUpdate.getUser_id()))
                .andExpect(jsonPath("$.user_name").value(userToUpdate.getUser_name()))
                .andExpect(jsonPath("$.user_surname").value(userToUpdate.getUser_surname()));
    }

    @Test
    @DisplayName("DELETE /api/v1/users/{id} - Should delete an existing user and return 204 No Content")
    public void shouldDeleteUserAndReturn204() throws Exception {
        Long userId = 1L;
        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                .with(user("leonardo").roles("ADMIN")).with(csrf()))
                .andExpect(status().isNoContent());
    }
}
