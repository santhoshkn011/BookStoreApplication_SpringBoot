package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;

import java.util.List;

public interface IUserService {
    UserDetails addUserData(UserDTO userDto);
    String insertData(UserDTO userDTO);

    List<UserDetails> getAllUserData();

    UserDetails getUserDataById(Long id);

    List<UserDetails> getUserDataByEmailAddress(String email);

    UserDetails updateDataByEmail(UserDTO userDTO, String email);

    UserDetails deleteData(Long id);
}