package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query(value = "SELECT * FROM cart WHERE user_id=:userId", nativeQuery = true)
    List<Cart> getCartListWithUserId(Long userId);
}
