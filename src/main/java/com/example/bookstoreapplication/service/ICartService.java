package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.model.Cart;

import java.util.List;

public interface ICartService {
    Cart addCartData(Long userId, CartDTO cartDTO);

    List<Cart> allCartList();

    Cart getCartDetailsByCartId(Long cartId);
    List<Cart> getCartDetailsByUserId(Long userId);

    String editCartByCartId(Long userId,Long cartId, CartDTO cartDTO);

    String deleteCartByCartId(Long cartId, Long UserId);

    List<Cart> getCartDetailsByToken(String token);
}