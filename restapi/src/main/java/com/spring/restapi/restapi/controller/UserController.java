package com.spring.restapi.restapi.controller;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.response.ResponseHandler;
import com.spring.restapi.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.spring.restapi.restapi.exception.UserNotFoundException;

import jakarta.validation.Valid;


@RestController
@CrossOrigin("http://localhost:3000/")
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
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseHandler.responseHandler("Invalid Data",HttpStatus.BAD_REQUEST);
            }
            return ResponseHandler.responseHandler("User Added", HttpStatus.CREATED, userService.addUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return ResponseHandler.responseHandler("Invalid Data",HttpStatus.BAD_REQUEST);
            }
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
