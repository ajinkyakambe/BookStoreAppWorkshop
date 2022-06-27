package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.BookDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")


public class BookDetailsData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookId;

    @Column( name = "book_name")
    private String bookName;

    @Column  (name = "book_author")
    private String bookAuthor;

    @Column (name = "book_description")
    private String bookDescription;

    @Column (name = "book_logo")
    private String bookLogo;

    @Column (name = "book_price")
    private Integer bookPrice;

    @Column (name = "book_quantity")
    private Integer bookQuantity;

    public  BookDetailsData (BookDetailsDTO bookDetailsDTO){
        this.updateBook(bookDetailsDTO);

    }

    public void updateBook (BookDetailsDTO bookDetailsDTO){
        this.bookName = bookDetailsDTO.getBookName();
        this.bookAuthor = bookDetailsDTO.getBookAuthor();
        this.bookDescription =  bookDetailsDTO.getBookDescription();
        this.bookLogo =  bookDetailsDTO.getBookLogo();
        this.bookPrice = bookDetailsDTO.getBookPrice();
        this.bookQuantity = bookDetailsDTO.getBookQuantity();

    }


}
