package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BookId", nullable = false)
    Long BookId;
    @Column(name = "name", nullable = false)
    String bookName;
    String authorName;
    String bookDescription;
    String bookImage;
    int price;
    int quantity;
    public Book(BookDTO bookdto){
        this.bookName = bookdto.getBookName();
        this.authorName = bookdto.getAuthorName();
        this.bookDescription = bookdto.getBookDescription();
        this.bookImage = bookdto.getBookImage();
        this.price = bookdto.getPrice();
        this.quantity = bookdto.getQuantity();
    }
}
