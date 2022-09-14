package com.example.bookstoreapplication.service;


import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.Orders;

import java.util.List;

public interface IOrderService {
    String addOrderDetails(OrderDTO orderDTO);

    Orders getOrderDetailsByOrderId(Long orderId);

    List<Orders> getOrderDetailsByUserId(Long userId);

    List<Orders> getOrderDetailsByToken(String token);

    String editOrderByOrderId(Long orderId, OrderDTO orderDTO);

    String deleteOrderByOrderId(Long userId, Long orderId);
}
