package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.service.UserService;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenUtility tokenUtility;
    //Home Page
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String greet() {
        return "Hello! This is Book Store Application Home Page";
    }
    //Insert data
//    @PostMapping("/insert")
//    public ResponseEntity<ResponseDTO> addUserData(@Valid @RequestBody UserDTO userDto) {
//        UserDetails userData = userService.addUserData(userDto);
//        ResponseDTO responseDTO = new ResponseDTO("Data Added Successfully", userData);
//        return new ResponseEntity(responseDTO, HttpStatus.ACCEPTED);
//    }
    //insert data using Utility layer and generated Token
    @PostMapping("/register")
    public ResponseEntity<String>AddUserDetails(@Valid @RequestBody UserDTO userDTO) {
        String token = userService.insertData(userDTO);
        ResponseDTO respDTO = new ResponseDTO("Data Added Successfully and email sent to the User", token);
        return new ResponseEntity(respDTO, HttpStatus.CREATED);
    }
    //Get User Data by token
    @GetMapping("/getUser/{token}")
    public ResponseEntity<String>getUserDetails(@PathVariable String token){
        UserDetails userData = userService.getUserDataByToken(token);
        Long Userid = tokenUtility.decodeToken(token);
        ResponseDTO respDTO = new ResponseDTO("Data retrieved successfully for the ID: "+Userid, userData);
        return new ResponseEntity(respDTO, HttpStatus.OK);
    }
    //Get all data
    @GetMapping("/allUser")
    public ResponseEntity<ResponseDTO> getAllUserDetails(){
        List<UserDetails> userDetailsList = userService.getAllUserData();
        ResponseDTO responseDTO = new ResponseDTO("All User Details, total count: "+ userDetailsList.size(),userDetailsList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get User Data by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable Long id){
        UserDetails userDetails = userService.getUserDataById(id);
        ResponseDTO responseDTO = new ResponseDTO("User Details with the ID: "+id, userDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get User Data by Email Address
    @GetMapping("/email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmail(@PathVariable String email){
        UserDetails userDetails = userService.getUserDataByEmailAddress(email);
        ResponseDTO responseDTO = new ResponseDTO("User Details with the Email Address: "+email, userDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Updating the User Data using email Address
    @PutMapping("/update/{email}")
    public ResponseEntity<ResponseDTO> updateUserByEmailAddress(@PathVariable String email,@Valid @RequestBody UserDTO userDTO) {
        UserDetails userData = userService.updateDataByEmail(userDTO, email);
        ResponseDTO respDTO= new ResponseDTO("Data Update info", userData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Delete the User details by User ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteUserDataByID(@PathVariable Long id) {
        UserDetails deletedData = userService.deleteData(id);
        ResponseDTO respDTO= new ResponseDTO("Deleted Successfully and e-mail sent, Below Data is deleted", deletedData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Login check
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        UserDetails response = userService.loginUser(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful!", response);
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}