package com.spring.restapi.restapi.service;

import com.spring.restapi.restapi.converter.UserConverter;
import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.entities.User;
import com.spring.restapi.restapi.exception.UserNotFoundException;
import com.spring.restapi.restapi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Test User Service")
class UserServiceImplTest {

    @Mock
    private UserDao userRepo;
    @InjectMocks
    private UserServiceImpl userService;
    private AutoCloseable autoCloseable;
    @Mock
    private UserConverter userConverter;
    private UserDto user;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userRepo = mock(UserDao.class);
        mock(User.class);
        this.userService = new UserServiceImpl(userRepo, userConverter);
        user = new UserDto(1, "Kailash", "kailash@gmail.com", "9816385093", "male", 26, "Nepali");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Get All User")
    void TestGetAllUser() {
        List<UserDto> userList = userService.getAllUser();
        when(userConverter.convertToDtoList(userRepo.findAll())).thenReturn(userList);

        assertEquals(userList.size(), userConverter.convertToDtoList(userRepo.findAll()).size());
        assertEquals(userList, userConverter.convertToDtoList(userRepo.findAll()));
    }

    @Test
    @DisplayName("Get By Id")
    void getUserById() throws Exception{
// Arrange
        int userId = 1;
        UserDto userDto = new UserDto(1, "Kailash", "kailash@gmail.com", "9816385093", "male", 26, "Nepali");
        User userEntity = new User(); // Create a User entity object here

        // Configure userConverter to return userEntity when converting to entity and back to DTO
        when(userConverter.convertToEntity(userDto)).thenReturn(userEntity);
        when(userConverter.convertToDto(userEntity)).thenReturn(userDto);

        // Configure userRepo to return userEntity for the given userId
        when(userRepo.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        UserDto retrievedUser = userService.getUserById(userId);

        // Assert
        assertNotNull(retrievedUser);
        assertEquals(userDto, retrievedUser);
    }

    @Test
    @DisplayName("Get User By Id (UserNotFoundException)")
    void getUserById_UserNotFoundException() {
        // Arrange
        int nonExistentUserId = 99; // An ID that doesn't exist in your test data
        when(userRepo.findById(nonExistentUserId)).thenReturn(Optional.empty()); // Configure findById to return empty Optional

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(nonExistentUserId));
    }

    @Test
    @DisplayName("Add User")
    void testAddUser() {
        // Arrange
        UserDto inputUserDto = user;
        User userEntity = new User(); // Create a User entity object here
        UserDto expectedUserDto = new UserDto(1, "Kailash", "kailash@gmail.com", "9816385093", "male", 26, "Nepali");

        when(userConverter.convertToEntity(inputUserDto)).thenReturn(userEntity);
        when(userRepo.saveAndFlush(userEntity)).thenReturn(userEntity);
        when(userConverter.convertToDto(userEntity)).thenReturn(expectedUserDto);

        // Act
        UserDto addedUser = userService.addUser(inputUserDto);

        // Assert
        assertEquals(expectedUserDto, addedUser);

    }

    @Test
    @DisplayName("Update User")
    void testUpdateUser() throws UserNotFoundException {
        // Arrange
        UserDto inputUserDto = new UserDto(1, "Nikita", "niki@gmail.com", "9816385093", "female", 26, "Nepali");
        User userEntity = new User(); // Create a User entity object here
        UserDto expectedUserDto = new UserDto(1, "Nikita", "niki@gmail.com", "9816385093", "female", 26, "Nepali");

        // Mock the userService
        UserService userServiceMock = Mockito.mock(UserService.class);

        when(userConverter.convertToEntity(inputUserDto)).thenReturn(userEntity);
        when(userRepo.save(userEntity)).thenReturn(userEntity);
        when(userConverter.convertToDto(userEntity)).thenReturn(expectedUserDto);

        // Configure the userServiceMock to throw UserNotFoundException
        doThrow(UserNotFoundException.class).when(userServiceMock).updateUser(inputUserDto);

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userServiceMock.updateUser(inputUserDto));

    }

    @Test
    @DisplayName("Delete User")
    void testDeleterUser() {
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1));

        verify(userRepo, never()).delete(any(User.class));

    }
}