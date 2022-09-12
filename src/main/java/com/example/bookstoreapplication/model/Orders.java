package com.example.bookstoreapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Data@AllArgsConstructor
@NoArgsConstructor
@ToString
//order is a predefined query taq in sql
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "orderId", nullable = false)
    private Long orderId;
    @JoinColumn(name="userId")
    @OneToOne(cascade = {CascadeType.ALL})
    private UserDetails user;
    @JoinColumn(name="BookId")
    @ManyToOne(cascade = {CascadeType.ALL})
    private Book book;
    int orderQuantity;
    double orderPrice;
    String address;
    LocalDate orderDate;
    boolean cancel;

    public Orders(UserDetails user, Book book, int orderQuantity, double orderPrice, String address, LocalDate orderDate, boolean cancel) {
        this.user = user;
        this.book = book;
        this.orderQuantity = orderQuantity;
        this.orderPrice = orderPrice;
        this.address = address;
        this.orderDate = orderDate;
        this.cancel = cancel;
    }

    public double calculateOrderPrice(){
        double orderPrice = this.book.getPrice()*this.orderQuantity;
        return orderPrice;
    }
}
