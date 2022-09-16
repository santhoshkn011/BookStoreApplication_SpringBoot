package com.example.bookstoreapplication.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BookOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_order_id", nullable = false)
    private Long bookOrderId;
    @JoinColumn(name="userId")
    @OneToOne(cascade = {CascadeType.ALL})
    private UserDetails user;
//    private List<Cart> cartList;
    @ManyToMany
    private List<Book> bookList;
    int orderQuantity;
    double orderPrice;
    String address;
    LocalDate orderDate;
    boolean cancel;
    public BookOrders(UserDetails user, List bookList, int orderQuantity, double orderPrice,String address, LocalDate orderDate, boolean cancel) {
        this.user = user;
//        this.cartList = cartList;
        this.bookList = bookList;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.address = address;
        this.orderDate = orderDate;
        this.cancel = cancel;
    }
}
