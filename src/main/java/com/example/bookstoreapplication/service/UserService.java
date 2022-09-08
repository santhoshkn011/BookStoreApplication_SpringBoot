package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails addUserData(UserDTO userDto) {
        UserDetails userData = new UserDetails(userDto);
        return userRepo.save(userData);
    }
}
