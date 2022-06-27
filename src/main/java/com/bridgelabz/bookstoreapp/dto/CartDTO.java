package com.bridgelabz.bookstoreapp.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

public @Data
class CartDTO {
    public int userId;
    public  int bookId;

    @NotNull(message="Book quantity to be provided")
    public  Integer quantity;
}
