package com.spring.restapi.restapi.controller;

import com.spring.restapi.restapi.dto.UserDto;
import com.spring.restapi.restapi.response.ResponseHandler;
import com.spring.restapi.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.spring.restapi.restapi.exception.UserNotFoundException;

import jakarta.validation.Valid;


@RestController
@Slf4j
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
            log.info("All data returned");
            return ResponseHandler.responseHandler("All User returned", HttpStatus.OK, this.userService.getAllUser());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Internal Server Error");
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
                log.info("User Returned for id: " + userId);
                return ResponseHandler.responseHandler(message, HttpStatus.OK, user);
            } else {
                log.error("User not found for id: "+ userId);
                return ResponseHandler.responseHandler(errorMessage, HttpStatus.NOT_FOUND, null);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            log.error("User not found for id: "+ userId);
            return ResponseHandler.responseHandler(errorMessage, HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.error("Bad Request "+ user);
                return ResponseHandler.responseHandler("Invalid Data",HttpStatus.BAD_REQUEST);
            }
            log.info("New User Added "+ user);
            return ResponseHandler.responseHandler("User Added", HttpStatus.CREATED, userService.addUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PutMapping("/users")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                log.error("Bad Request "+ user);
                return ResponseHandler.responseHandler("Invalid Data",HttpStatus.BAD_REQUEST);
            }
            log.info("User Updated"+ user);
            return ResponseHandler.responseHandler("User update Successful", HttpStatus.OK, this.userService.updateUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable int userId) {
        try {
            this.userService.deleteUser(userId);
            log.info("User Deleted for id: "+ userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted");
        } catch (Exception e) {
            log.error("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
