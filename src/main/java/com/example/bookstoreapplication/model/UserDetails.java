package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "user")
public @Data class UserDetails {
    //User Entities
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId", nullable = false)
    Long userId;
    String firstName;
    String lastName;
    String address;
    @Column(name = "email", nullable = false)
    String emailAddress;
    LocalDate DOB;
    String password;

    public UserDetails(UserDTO userdto){
        this.firstName = userdto.getFirstName();
        this.lastName = userdto.getLastName();
        this.address = userdto.getAddress();
        this.emailAddress = userdto.getEmailAddress();
        this.DOB = userdto.getDOB();
        this.password = userdto.getPassword();
    }
}