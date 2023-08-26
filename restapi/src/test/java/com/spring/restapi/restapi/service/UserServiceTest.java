package com.spring.restapi.restapi.service;

import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.entities.User;
import com.spring.restapi.restapi.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Test User Service")
class UserServiceTest {

    @Mock
    private UserDao userRepo;
    private UserService userService;
    AutoCloseable autoCloseable;
    User user;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userRepo = mock(UserDao.class);
        mock(User.class);
        this.userService = new UserService(userRepo);
        user = new User(1, "Kailash", "kailash@gmail.com", "9816385093", "male", 26, "Nepali");

    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Get All User")
    void TestGetAllUser() {
        List<User> userList = userService.getAllUser();
        when(userRepo.findAll()).thenReturn(userList);

        assertEquals(userList.size(), userRepo.findAll().size());
        assertEquals(userList, userRepo.findAll());
    }

    @Test
    @DisplayName("Get By Id")
    void getUserById() {
        try {
            when(userRepo.findById(1)).thenReturn(Optional.ofNullable(user));
            User retrievedUser = userService.getUserById(1);

            assertNotNull(retrievedUser);
            assertEquals(user, retrievedUser);
        } catch (UserNotFoundException ex) {
            fail("UserNotFoundException should not have been thrown");
        }
    }

    @Test
    @DisplayName("Add User")
    void testAddUser() {
        when(userRepo.save(user)).thenReturn(user);

        assertEquals(user, userService.addUser(user));
    }

    @Test
    @DisplayName("Update User")
    void testUpdateUser() {
        when(userRepo.save(user)).thenReturn(user);

        assertEquals(user, userService.addUser(user));
    }

    @Test
    @DisplayName("Delete User")
    void testDeleterUser() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleterUser(1));

        verify(userRepo, never()).delete(any(User.class));

    }
}