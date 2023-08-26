package com.spring.restapi.restapi.repository;

import com.spring.restapi.restapi.dao.UserDao;
import com.spring.restapi.restapi.entities.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepoTest {
    // given - when - then

    @Autowired
    private UserDao userRepo;
    List<User> users;
    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User(1, "Kailash", "kailash@gmail.com", "9816385093", "male", 26, "Nepali"),
                new User(2, "Nikita", "niki@gmail.com", "9816855093", "female", 26, "Nepali"),
                new User(3, "Omkar", "om@gmail.com", "9810385093", "male", 26, "Nepali"));
        userRepo.saveAll(users);
    }

    @AfterEach
    void tearDown() {
        users = null;
        userRepo.deleteAll();
    }

    //Test Case SUCCESS
    @Test
    void testFindAllUser(){
        List<User> user = userRepo.findAll();
        System.out.println("Retrieved user: " + user); // Debug print
        assertNotNull(user); // Check that the retrieved user is not null
        assertEquals(3, user.size()); // Check that the user's ID is 20
    }

    //Test Case FAILURE


    //Test Case SUCCESS
    @Test
    void testFindById(){
        List<User> userById = userRepo.findAll().stream()
                .filter(u -> u.getUserId() == 1)
                .collect(Collectors.toList());
        System.out.println("Retrieved user: " + userById); // Debug print
        assertNotNull(userById); // Check that the retrieved user is not null
        assertEquals(1, userById.get(0).getUserId()); // Check that the user's ID is 20
    }

    //Test Case FAILURE
    @Test
    void testFindById_Fail(){
        User userById = userRepo.findById(5).orElse(null);
        System.out.println("Retrieved user: " + userById); // Debug print
        assertNull(userById); // Check that the retrieved user is null
        assertEquals(null, userById); // Check that the user's ID is 20
    }
}
