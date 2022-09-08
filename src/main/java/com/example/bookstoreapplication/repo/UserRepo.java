package com.example.bookstoreapplication.repo;

import com.example.bookstoreapplication.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Long> {
    @Query(value = "SELECT * FROM book_store WHERE user_id = user_id and email=:email", nativeQuery = true)
    List<UserDetails> findByEmailAddress(String email);
}