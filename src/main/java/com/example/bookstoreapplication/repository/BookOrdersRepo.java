package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.BookOrders;
import com.example.bookstoreapplication.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookOrdersRepo extends JpaRepository<BookOrders, Long> {
    @Query(value = "SELECT * FROM book_orders WHERE user_id=:userId", nativeQuery = true)
    List<BookOrders> findAllById(Long userId);
}
