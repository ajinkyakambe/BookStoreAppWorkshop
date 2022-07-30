package com.bridgelabz.bookstoreapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private LocalDate orderDate = LocalDate.now();
    private Integer price;
    private Integer quantity;
    private String address;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserRegistrationData userId;

    @ManyToOne
    @JoinColumn(name="book_id")
    private BookDetailsData bookId;

    private boolean cancel;


    public OrderData(Integer price, Integer quantity, String address, BookDetailsData book, UserRegistrationData user) {
        this.price =  price;
        this.quantity = quantity;
        this.address = address;
        this.userId = user;
        this.bookId = book;
        


    }
}
