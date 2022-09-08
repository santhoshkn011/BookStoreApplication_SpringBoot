package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name = "BookStore")
public @Data class UserDetails {
    //User Entities
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    Long userId;
    @Column(name="first_name")
    String firstName;
    String lastName;
    String address;
    @ElementCollection
    @CollectionTable(name = "email_address", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "emails")
    List<String> emailAddress;
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
