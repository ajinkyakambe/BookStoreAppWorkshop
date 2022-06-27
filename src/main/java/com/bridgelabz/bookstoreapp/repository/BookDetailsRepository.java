package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.BookDetailsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookDetailsRepository  extends JpaRepository<BookDetailsData, Integer> {

    @Query(value = "SELECT * from books where book_name = :bookName", nativeQuery = true)
    List<BookDetailsData> findByBookName(String bookName);

    @Query(value = "SELECT * from books ORDER BY book_price", nativeQuery = true)
    List<BookDetailsData> sortBooksByAsc();

    @Query(value = "SELECT * from books ORDER BY book_price DESC", nativeQuery = true)
    List<BookDetailsData> sortBooksByDesc();

}