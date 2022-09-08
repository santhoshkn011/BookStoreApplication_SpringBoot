package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;

public interface IUserService {
    UserDetails addUserData(UserDTO userDto);
}
