package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.exception.UserException;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repo.UserRepo;
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
        String token = tokenUtility.createToken(userDetails.getUser_id());
        //email body
        String userData = "ADDED DETAILS: \n" + "First Name: " + userDetails.getFirst_name() + "\n" + "Last Name: " + userDetails.getLast_name() + "\n"
                + "Address: " + userDetails.getAddress() + "\n" + "Email Address: " + userDetails.getEmail_address() + "\n" + "DOB: " + userDetails.getDOB()+"\n"
                +"Password: " + userDetails.getPassword();;
        //sending email
        emailSender.sendEmail(userDetails.getEmail_address(), "Data Added!!!", userData);
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
    public List<UserDetails> getUserDataByEmailAddress(String email) {
        List<UserDetails> userDetails = userRepo.findByEmailAddress(email);
        if (userDetails.isEmpty()) {
            throw new UserException("Email Address: " + email + ", does not exist");
        } else
            return userDetails;
    }

    //Update data by email address
    @Override
    public UserDetails updateDataByEmail(UserDTO userDTO, String email) {
        List<UserDetails> userDetails = userRepo.findByEmailAddress(email);
        Optional<UserDetails> userData = Optional.ofNullable(userDetails.get(0));
        if (userData.isPresent()) {
            userDetails.get(0).setFirst_name(userDTO.getFirst_name());
            userDetails.get(0).setLast_name(userDTO.getLast_name());
            userDetails.get(0).setAddress(userDTO.getAddress());
            userDetails.get(0).setEmail_address(userDTO.getEmail_address());
            userDetails.get(0).setDOB(userDTO.getDOB());
            userDetails.get(0).setPassword(userDTO.getPassword());
            //Email Body
            String updatedData = "UPDATED DETAILS: \n" + "First Name: " + userDetails.get(0).getFirst_name() + "\n" + "Last Name: " + userDetails.get(0).getLast_name() + "\n"
                    + "Address: " + userDetails.get(0).getAddress() + "\n" + "Email Address: " + userDetails.get(0).getEmail_address() + "\n" + "DOB: " + userDetails.get(0).getDOB() +"\n"
                    + "Password: " + userDetails.get(0).getPassword();
            //sending email
            emailSender.sendEmail(userDetails.get(0).getEmail_address(), "Data Updated!!!", updatedData);

            return userRepo.save(userDetails.get(0));
        } else
            throw new UserException("Invalid Email Address: " + email);
    }
    //Delete data by id
    @Override
    public UserDetails deleteData(Long id) {
        UserDetails userDetails = userRepo.findById(id).orElse(null);
        if(userDetails != null){
            userRepo.deleteById(id);
            //sending email
            emailSender.sendEmail(userDetails.getEmail_address(), "Data Deleted!!!", "Your Data deleted successfully from the Book Store Application!!");
        }else
            throw new UserException("Error: Cannot find User ID " + id);
        return userDetails;
    }
}