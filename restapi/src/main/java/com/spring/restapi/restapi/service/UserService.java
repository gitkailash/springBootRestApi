package com.spring.restapi.restapi.service;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public List<UserDto> getAllUser();
    public UserDto getUserById(int userId) throws UserNotFoundException;
    public UserDto addUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto) throws UserNotFoundException;
    public void deleteUser(int userId) throws UserNotFoundException;
}
