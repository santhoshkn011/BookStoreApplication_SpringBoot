package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    ICartService cartService;

    //Add Cart Details to the UserID
    @PostMapping("/insert/{userId}")
    public ResponseEntity<ResponseDTO> addCartDetails(@PathVariable Long userId, @RequestBody CartDTO cartDTO){
        Cart cartDetails = cartService.addCartData(userId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details Added", cartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get All Cart Details
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllCartDetails(){
        List<Cart> cartList = cartService.allCartList();
        ResponseDTO responseDTO = new ResponseDTO("Cart List, total count: "+cartList.size(), cartList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Cart Data by CartId
    @GetMapping("/data/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDataByID(@PathVariable Long cartId){
        Cart cartDetails = cartService.getCartDetailsByCartId(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details with Cart ID: "+cartId, cartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get Cart Data by UserId
    @GetMapping("/UserCart/{userId}")
    public ResponseEntity<ResponseDTO> getCartDataByUserID(@PathVariable Long userId){
        List<Cart> userCartDetails = cartService.getCartDetailsByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("Cart Details with User ID: "+userId, userCartDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Update Cart Details By CartId
    @PutMapping("/update/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Long cartId, @RequestBody CartDTO cartDTO){
        String response = cartService.editCartByCartId(cartId, cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated Cart Data with Cart ID: "+cartId, response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
