package com.bridgelabz.bookstoreapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public @Data class BookDetailsDTO {
    public String bookName;
    public String bookAuthor;
    public String bookDescription;
    public String bookLogo;
    public int bookPrice;
    public int bookQuantity;

}
