package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
//    UserDetails addUserData(UserDTO userDto);
    String insertData(UserDTO userDTO);

    List<UserDetails> getAllUserData();

    UserDetails getUserDataById(Long id);

    UserDetails getUserDataByEmailAddress(String email);

    UserDetails updateDataByEmail(UserDTO userDTO, String email);

    UserDetails deleteData(Long id);

    UserDetails getUserDataByToken(String token);

    UserDetails loginUser(LoginDTO loginDTO);
}