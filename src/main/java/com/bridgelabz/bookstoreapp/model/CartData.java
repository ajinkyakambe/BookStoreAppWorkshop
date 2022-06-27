package com.bridgelabz.bookstoreapp.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "cart")
public class CartData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int cartId;

    //@OneToOne Relationship//
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserRegistrationData user;

    //@OneToOne Relationship //
    @OneToOne
    @JoinColumn(name = "book_id")
    private BookDetailsData book;

    @Column(name = "quantity")
    private Integer quantity;


    public CartData(Integer quantity, BookDetailsData book, UserRegistrationData user) {
        super();
        this.quantity = quantity;
        this.book = book;
        this.user = user;
    }

    public CartData() {

    }


}
