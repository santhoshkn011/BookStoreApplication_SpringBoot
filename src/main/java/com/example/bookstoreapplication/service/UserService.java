package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.LoginDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.exception.UserException;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repo.UserRepo;
import com.example.bookstoreapplication.utility.EmailSenderService;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;

    //Save the Data
    @Override
    public UserDetails addUserData(UserDTO userDto) {
        UserDetails userData = new UserDetails(userDto);
        return userRepo.save(userData);
    }

    //Generated token after saving data
    @Override
    public String insertData(UserDTO userDTO) throws UserException {
        UserDetails userDetails = new UserDetails(userDTO);
        userRepo.save(userDetails);
        String token = tokenUtility.createToken(userDetails.getUserId());
        //email body
        String userData = "ADDED DETAILS: \n" + "First Name: " + userDetails.getFirstName() + "\n" + "Last Name: " + userDetails.getLastName() + "\n"
                + "Address: " + userDetails.getAddress() + "\n" + "Email Address: " + userDetails.getEmailAddress() + "\n" + "DOB: " + userDetails.getDOB()+"\n"
                +"Password: " + userDetails.getPassword();;
        //sending email
        emailSender.sendEmail(userDetails.getEmailAddress(), "Data Added!!!", userData);
        return token;
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
            //Email Body
            String updatedData = "UPDATED DETAILS: \n" + "First Name: " + userDetails.getFirstName() + "\n" + "Last Name: " + userDetails.getLastName() + "\n"
                    + "Address: " + userDetails.getAddress() + "\n" + "Email Address: " + userDetails.getEmailAddress() + "\n" + "DOB: " + userDetails.getDOB() +"\n"
                    + "Password: " + userDetails.getPassword();
            //sending email
            emailSender.sendEmail(userDetails.getEmailAddress(), "Data Updated!!!", updatedData);

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