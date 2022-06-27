package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;

import java.time.LocalDate;

public @Data
class OrderDTO {
    public Integer  price;
    public Integer  quantity;
    public String   address;
    public Integer  userId;
    public Integer  bookId;
    public LocalDate orderDate = LocalDate.now();
}
