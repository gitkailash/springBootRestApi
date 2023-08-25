package com.spring.restapi.restapi.service;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.entities.User;
import com.spring.restapi.restapi.exception.UserNotFoundException;

@AllArgsConstructor
@Service
public class UserService {

    private final UserDao userDao;

    public List<User> getAllUser() {
        return userDao.findAll();
    }

    public User getUserById(int userId) throws UserNotFoundException {
        User user = userDao.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found for id ::" + userId));
        return user;
    }

    @Transactional
    public User addUser(User user) {
        userDao.save(user);
        return user;
    }

    @Transactional
    public User updateUser(User user) throws UserNotFoundException{
        User userDetails = this.userDao.findById(user.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found for id ::" + user.getUserId()));
        userDetails.setName(user.getName());
        userDetails.setEmail(user.getEmail());
        userDetails.setGender(user.getGender());
        userDetails.setAge(user.getAge());
        userDetails.setMobile(user.getMobile());
        userDetails.setNationality(user.getNationality());
        userDao.save(userDetails);
        return user;
    }

    @Transactional
    public void deleterUser(int userId) throws UserNotFoundException {
        User user = this.userDao.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found for id ::" + userId));
        userDao.delete(user);
    }
}
