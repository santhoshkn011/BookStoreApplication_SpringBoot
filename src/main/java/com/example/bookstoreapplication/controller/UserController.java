package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/bookStore")
public class UserController {
    @Autowired
    UserService userService;
    //Home Page
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String greet() {
        return "Hello! This is Book Store Application Home Page";
    }
    //Insert data
    @PostMapping("/insert")
    public ResponseEntity<UserDTO> addUserData(@Valid @RequestBody UserDTO userDto) {
        UserDetails userData = userService.addUserData(userDto);
        ResponseDTO responseDTO = new ResponseDTO("Data Added Successfully", userData);
        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
    }
}
