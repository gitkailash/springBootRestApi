package com.spring.restapi.restapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseHandler(String message, HttpStatus httpStatus, Object responseObject){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", httpStatus);
        response.put("data", responseObject);
        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<Object> responseHandler(String message, HttpStatus httpStatus){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }
}
