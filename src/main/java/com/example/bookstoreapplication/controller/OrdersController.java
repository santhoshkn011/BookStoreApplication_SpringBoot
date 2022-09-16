package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.dto.OrdersDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.BookOrders;
import com.example.bookstoreapplication.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    //Add Order Details and send email confirmation
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addOrderDetails(@RequestBody OrdersDTO ordersDTO){
        BookOrders response = ordersService.addOrderDetails(ordersDTO);
        ResponseDTO responseDTO = new ResponseDTO("Order Details Added", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get all order details of user ID
    @GetMapping("/getAllOrder/{userId}")
    public ResponseEntity<ResponseDTO> getAllOrderItems(@PathVariable Long userId) {
        List<BookOrders> listOfOrders = ordersService.getAllOrderItemsOfUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("All orders of user ID: "+userId, listOfOrders);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}
