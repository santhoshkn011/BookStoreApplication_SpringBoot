package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Long> {
    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    UserDetails findByEmailAddress(String email);
}