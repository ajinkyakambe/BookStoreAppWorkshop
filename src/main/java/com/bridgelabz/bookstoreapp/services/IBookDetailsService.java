package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.BookDetailsDTO;
import com.bridgelabz.bookstoreapp.model.BookDetailsData;

import java.util.List;

public interface IBookDetailsService {
    List<BookDetailsData> showAllBooks();

    BookDetailsData addBook(String token, BookDetailsDTO bookDto);

    BookDetailsData getBookById(int bookId);

    BookDetailsData updateBook(int bookId, BookDetailsDTO bookDTO);

    void deleteBook(int bookId);

    BookDetailsData updateBookQuantity(int bookId, int bookQuantity);

    List<BookDetailsData> getBookByName(String bookName); //find by book name

    List<BookDetailsData> sortBooksAsc(); //list of book in ascending order

    List<BookDetailsData> sortBooksDesc(); //list of book in descending order
}
