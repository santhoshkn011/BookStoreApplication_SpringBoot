package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.dto.OrdersDTO;
import com.example.bookstoreapplication.model.BookOrders;

import java.util.List;

public interface IOrdersService {
    BookOrders addOrderDetails(OrdersDTO ordersDTO);

    List<BookOrders> getAllOrderItemsOfUserId(Long userId);
}
