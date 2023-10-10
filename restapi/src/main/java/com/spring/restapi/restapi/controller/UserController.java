package com.spring.restapi.restapi.controller;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.response.ResponseHandler;
import com.spring.restapi.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.restapi.restapi.exception.UserNotFoundException;

import jakarta.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Welcome To Rest API";
    }

    @GetMapping({"/users", "/users/"})
    public ResponseEntity getAllUser() {
        try {
            return ResponseHandler.responseHandler("All User returned", HttpStatus.OK, this.userService.getAllUser());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable int userId) {
        String errorMessage = "user not found for given id :: " + userId;
        String message = "User fetched for id:: " + userId;
        try {
            UserDto user = this.userService.getUserById(userId);
            if (user != null) {
                return ResponseHandler.responseHandler(message, HttpStatus.OK, user);
            } else {
                return ResponseHandler.responseHandler(errorMessage, HttpStatus.NOT_FOUND, null);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseHandler.responseHandler(errorMessage, HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto user) {
        try {
            return ResponseHandler.responseHandler("User Added", HttpStatus.CREATED, userService.addUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto user) {
        try {
            return ResponseHandler.responseHandler("User update Successful", HttpStatus.OK, this.userService.updateUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId) {
        try {
            this.userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
