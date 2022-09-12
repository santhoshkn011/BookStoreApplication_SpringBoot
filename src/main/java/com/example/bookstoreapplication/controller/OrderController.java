package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.Orders;
import com.example.bookstoreapplication.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    //Add Order Details and send email confirmation
    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addOrderDetails(@RequestBody OrderDTO orderDTO){
        String response = orderService.addOrderDetails(orderDTO);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details Added", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order details by order ID
    @GetMapping("/data/{orderId}")
    public ResponseEntity<ResponseDTO> getOrderDataByOrderID(@PathVariable Long orderId){
        Orders orderDetails = orderService.getOrderDetailsByOrderId(orderId);
        ResponseDTO responseDTO = new ResponseDTO("Order Details with Order ID: "+orderId, orderDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order details by User ID
    @GetMapping("/orderDataByUser/{userId}")
    public ResponseEntity<ResponseDTO> getOrderDataByUserID(@PathVariable Long userId){
        List<Orders> ordersList = orderService.getOrderDetailsByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("Order Details with User ID: "+userId, ordersList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Order Data by Token
    @GetMapping("/orderDataByToken/{token}")
    public ResponseEntity<ResponseDTO> getOrderDataByToken(@PathVariable String token){
        List<Orders> ordersList = orderService.getOrderDetailsByToken(token);
        ResponseDTO responseDTO = new ResponseDTO("Order Details with Token: "+token, ordersList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
