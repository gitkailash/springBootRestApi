package com.spring.restapi.restapi.controller;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.exception.UserNotFoundException;
import com.spring.restapi.restapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserServiceImpl userService;
    private AutoCloseable autoCloseable;
    List<UserDto> userList;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userList = Arrays.asList(
                new UserDto(1, "Kailash", "kailash@gmail.com", "9816385003", "male", 26, "Nepali"),
                new UserDto(2, "Nikita", "niki@gmail.com", "9816850093", "female", 26, "Nepali"),
                new UserDto(3, "Omkar", "om@gmail.com", "9815585093", "male", 26, "Nepali")
        );

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getAllUser() throws Exception {
        when(userService.getAllUser()).thenReturn(userList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(userList.size())));
    }

    @Test
    @DisplayName("Get User By Id")
    void testGetUser() throws Exception {
        int userId = 1;
        Optional<UserDto> user = userList.stream().filter(u -> u.getUserId() == userId).findFirst();
        System.out.println(user);
        when(userService.getUserById(userId)).thenReturn(user.orElse(null));

        if (user.isPresent()) {
            this.mockMvc.perform(get("/api/users/{userId}", userId)).andExpect(status().isOk());
        } else if (user.isEmpty()) {
            this.mockMvc.perform(get("/api/users/{userId}", userId)).andExpect(status().isNotFound());
        }
    }

    @Test
    @DisplayName("Get User Class Not Found Exception")
    void testGetUserNotFoundException() throws Exception {
        int userId = 5;
        when(userService.getUserById(userId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/users/{userId}", userId)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Add User")
    void addUser() throws Exception {
        UserDto newUser = new UserDto(6, "Nabin", "Nabin@gmail.com", "9818525093", "male", 26, "Nepali");
        when(userService.addUser(newUser)).thenReturn(newUser);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 6, \"name\": \"Nabin\", \"email\": \"Nabin@gmail.com\", \"phone\": \"9818525093\", \"gender\": \"male\", \"age\": 26, \"nationality\": \"Nepali\"}")
                )
                .andExpect(status().isCreated());
        assertEquals(newUser, userService.addUser(newUser));
    }

    @Test
    @DisplayName("Update User")
    void updateUser() throws Exception {
        UserDto updateUser = new UserDto(1, "Nabin", "Nib@gmail.com", "9818525093", "male", 20, "Nepali");
        when(userService.updateUser(updateUser)).thenReturn(updateUser);

        mockMvc.perform(put("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Nabin\", \"email\": \"Nib@gmail.com\", \"phone\": \"9818525093\", \"gender\": \"male\", \"age\": 20, \"nationality\": \"Nepali\"}")
                )
                .andExpect(status().isOk());
        assertEquals(updateUser, userService.updateUser(updateUser));
    }

    @Test
    @DisplayName("Delete User")
    void deleteUser() throws Exception {
        int userId = 1;
        mockMvc.perform(delete("/api/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

        verify(userService, times(1)).deleteUser(userId);
    }
}