package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name = "book_store")
public @Data class UserDetails {
    //User Entities
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    Long user_id;
    String first_name;
    String last_name;
    String address;
    @Column(name = "email", nullable = false)
    String email_address;
    LocalDate DOB;
    String password;

    public UserDetails(UserDTO userdto){
        this.first_name = userdto.getFirst_name();
        this.last_name = userdto.getLast_name();
        this.address = userdto.getAddress();
        this.email_address = userdto.getEmail_address();
        this.DOB = userdto.getDOB();
        this.password = userdto.getPassword();
    }
}