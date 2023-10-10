package com.spring.restapi.restapi.service.impl;

import java.util.List;

import com.spring.restapi.restapi.converter.UserConverter;
import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.entities.User;
import com.spring.restapi.restapi.exception.UserNotFoundException;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserConverter userConverter;

    @Override
    public List<UserDto> getAllUser() {
        return userConverter.convertToDtoList(userDao.findAll());
    }

    @Override
    public UserDto getUserById(int userId) throws UserNotFoundException {
        User user = userDao.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found for id ::" + userId));
        return userConverter.convertToDto(user);
    }

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        User userEntity = userConverter.convertToEntity(userDto);
        return userConverter.convertToDto(userDao.saveAndFlush(userEntity));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto) throws UserNotFoundException{
        User userDetails = this.userDao.findById(userDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found for id ::" + userDto.getUserId()));
        userDetails.setName(userDto.getName());
        userDetails.setEmail(userDto.getEmail());
        userDetails.setGender(userDto.getGender());
        userDetails.setAge(userDto.getAge());
        userDetails.setMobile(userDto.getMobile());
        userDetails.setNationality(userDto.getNationality());
        return userConverter.convertToDto(userDao.saveAndFlush(userDetails));
    }

    @Override
    @Transactional
    public void deleteUser(int userId) throws UserNotFoundException {
        User user = this.userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for id ::" + userId));
        userDao.delete(user);
    }
}
