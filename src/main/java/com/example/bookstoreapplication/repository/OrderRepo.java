package com.example.bookstoreapplication.repository;

import com.example.bookstoreapplication.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders, Long> {
    @Query(value = "SELECT * FROM orders WHERE user_id=:userId", nativeQuery = true)
    List<Orders> getOrderListWithUserId(Long userId);
}
