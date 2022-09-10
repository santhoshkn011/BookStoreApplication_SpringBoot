package com.example.bookstoreapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public @ToString class BookDTO {
    //Add Validation later
    String bookName;
    String authorName;
    String bookDescription;
    String bookImage;
    int price;
    int quantity;
}
