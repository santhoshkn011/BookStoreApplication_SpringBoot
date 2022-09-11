package com.example.bookstoreapplication.model;

import com.example.bookstoreapplication.dto.CartDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cartId", nullable = false)
    Long cartId;
    @JoinColumn(name="userId")
    @OneToOne(cascade = {CascadeType.ALL})
    UserDetails user;
    @JoinColumn(name="BookId")
    @ManyToOne(cascade = {CascadeType.ALL})
    Book book;
    int quantity;

    public Cart(UserDetails user, Book book, int quantity) {
        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }
}
