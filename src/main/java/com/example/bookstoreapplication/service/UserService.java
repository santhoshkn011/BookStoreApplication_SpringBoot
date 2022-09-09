package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.exception.UserException;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repository.UserRepo;
import com.example.bookstoreapplication.utility.EmailSenderService;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;

    //Save the Data
//    @Override
//    public UserDetails addUserData(UserDTO userDto) {
//        UserDetails userData = new UserDetails(userDto);
//        return userRepo.save(userData);
//    }

    //Generated token after saving data and sent email
    @Override
    public String insertData(UserDTO userDTO) throws UserException {
        UserDetails userDetails = new UserDetails(userDTO);
        userRepo.save(userDetails);
        String token = tokenUtility.createToken(userDetails.getUserId());
        //sending email
        emailSender.sendEmail(userDetails.getEmailAddress(), "Data Added!!!", "Your Account is registered! Please Click on the below link for the details."+"\n"+"http://localhost:9090/user/getUser/"+token);
        return token;
    }
    //Get User Details by Token
    @Override
    public UserDetails getUserDataByToken(String token) {
        Long Userid = tokenUtility.decodeToken(token);
        Optional<UserDetails> existingData = userRepo.findById(Userid);
        if(existingData.isPresent()){
            return existingData.get();
        }else
            throw new UserException("Invalid Token");
    }
    //Login check
    @Override
    public UserDetails loginUser(LoginDTO loginDTO) {
        Optional<UserDetails> userDetails = Optional.ofNullable(userRepo.findByEmailAddress(loginDTO.getEmailAddress()));
        if (userDetails.isPresent()) {
            String passWord = userDetails.get().getPassword();
            if(passWord == loginDTO.getPassword()) {
                emailSender.sendEmail(userDetails.get().getEmailAddress(), "Login", "Login Successful!");
                return userDetails.get();
            } else
                emailSender.sendEmail(userDetails.get().getEmailAddress(), "Login", "You have entered Invalid password!");
                throw new UserException("Wrong Password!!!");
        }else
            throw new UserException("Login Failed, Entered wrong email or password!!!");
    }

    //Get all User Details list
    @Override
    public List<UserDetails> getAllUserData() {
        List<UserDetails> userDetailsList = userRepo.findAll();
        if (userDetailsList.isEmpty()) {
            throw new UserException("No User Registered yet!!!!");
        } else
            return userDetailsList;
    }

    //Get the user data by id
    @Override
    public UserDetails getUserDataById(Long id) {
        UserDetails userDetails = userRepo.findById(id).orElse(null);
        if (userDetails != null) {
            return userDetails;
        } else
            throw new UserException("ID: " + id + ", does not exist");
    }

    //Get the User Details by Email Address
    @Override
    public UserDetails getUserDataByEmailAddress(String email) {
        UserDetails userDetails = userRepo.findByEmailAddress(email);
        if (userDetails != null) {
            return userDetails;
        } else
            throw new UserException("Email Address: " + email + ", does not exist");
    }

    //Update data by email address
    @Override
    public UserDetails updateDataByEmail(UserDTO userDTO, String email) {
        UserDetails userDetails = userRepo.findByEmailAddress(email);
        if (userDetails != null) {
            userDetails.setFirstName(userDTO.getFirstName());
            userDetails.setLastName(userDTO.getLastName());
            userDetails.setAddress(userDTO.getAddress());
            userDetails.setEmailAddress(userDTO.getEmailAddress());
            userDetails.setDOB(userDTO.getDOB());
            userDetails.setPassword(userDTO.getPassword());

            String token = tokenUtility.createToken(userDetails.getUserId());
            //sending email
            emailSender.sendEmail(userDetails.getEmailAddress(), "Data Updated!!!", "Please Click on the below link for the updated details."+"\n"+"http://localhost:9090/user/getUser/"+token);

            return userRepo.save(userDetails);
        } else
            throw new UserException("Invalid Email Address: " + email);
    }
    //Delete data by id
    @Override
    public UserDetails deleteData(Long id) {
        UserDetails userDetails = userRepo.findById(id).orElse(null);
        if(userDetails != null){
            //sending email
            emailSender.sendEmail(userDetails.getEmailAddress(), "Data Deleted!!!", "Your Data deleted successfully from the Book Store Application!!");
            userRepo.deleteById(id);
        }else
            throw new UserException("Error: Cannot find User ID " + id);
        return userDetails;
    }

}