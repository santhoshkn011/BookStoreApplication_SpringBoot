package com.example.bookstoreapplication.dto;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public @ToString class CartDTO {
    Long bookId;
    int quantity;

    public CartDTO(Long bookId, int quantity){
        this.bookId=bookId;
        this.quantity=quantity;
    }
}
