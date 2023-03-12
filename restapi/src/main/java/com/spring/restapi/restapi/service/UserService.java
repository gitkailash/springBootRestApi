package com.spring.restapi.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.entities.User;
import com.spring.restapi.restapi.exception.UserNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserService() {
    }

    public List<User> getAllUser() {

        return userDao.findAll();
    }

    public Optional<User> getSingleUser(int userId) throws UserNotFoundException {
        Optional<User> u = userDao.findById(userId);
        if (u != null) {
            return u;
        } else {
            throw new UserNotFoundException("User Not Found with Id");
        }

    }

    public User addUser(User user) {
        userDao.save(user);
        return user;

    }

    public User updateUser(User user) {
        userDao.save(user);
        return user;
    }

    public void deleterUser(int userId) {
        User user = this.userDao.getReferenceById(userId);
        userDao.delete(user);
    }
}
