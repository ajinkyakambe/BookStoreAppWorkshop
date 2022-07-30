package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.BookDetailsDTO;
import com.bridgelabz.bookstoreapp.exception.UserRegistrationException;
import com.bridgelabz.bookstoreapp.model.BookDetailsData;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;
import com.bridgelabz.bookstoreapp.repository.BookDetailsRepository;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.EmailSMTP;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class BookDetailsService implements IBookDetailsService {

    @Autowired
    BookDetailsRepository bookRepo;
    @Autowired
    UserRegistrationRepository userRepo;


    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSMTP mailService;

    /**
     * created a method name as showAllBooks
     * - Ability to get all book data
     */
    @Override
    public List<BookDetailsData> showAllBooks() {
        List<BookDetailsData> allBooks = bookRepo.findAll();
        return allBooks;

    }

    /**
     * created a method name as addBook
     * - Ability to save book details to repository
     */
    @Override
    public BookDetailsData addBook(String token,BookDetailsDTO bookDto) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isPresent = userRepo.findById(id);
        if (isPresent.isPresent()) {
            BookDetailsData bookAdded = new BookDetailsData(bookDto);
            bookRepo.save(bookAdded);
            // mailService.sendEmail("ajinkyakambe@gmail.com", "Book Details", "Added SuccessFully,\nBook Name: "
            //         + bookDto.getBookName()+ "\nBook Author - " + bookDto.getBookAuthor() + "\nBook Price - " + bookDto.getBookPrice() +
            //         "\nQuantity - "+ bookDto.getBookQuantity());
            return bookAdded;
        }
        return null;
    }

    /**
     * created a method name as getBookById
     * - Ability to get book data by id
     */
    @Override
    public BookDetailsData getBookById(int bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new UserRegistrationException("Book  with id " + bookId + " does not exist in database..!"));
    }

    /**
     * created a method name as getBookByName
     * ability to get data by particular book Name
     * */
    @Override
    public List<BookDetailsData> getBookByName(String bookName) {
        return bookRepo.findByBookName(bookName);
    }

    /**
     * created a method name as updateBook
     * Ability to update book data for particular id
     * */
    @Override
    public BookDetailsData updateBook(int bookId, BookDetailsDTO bookDTO) {
        BookDetailsData bookData = this.getBookById(bookId);
        bookData.updateBook(bookDTO);
        return bookRepo.save(bookData);
    }

    /**
     * create a method name as deleteBook
     * ability to delete data by particular book id
     * */
    @Override
    public void deleteBook( int bookId) {
        BookDetailsData isBookPresent = this.getBookById( bookId);
        bookRepo.delete(isBookPresent);
    }
    /**
     * created a method name as updateBookQuantity
     * ability to update data by particular book id
     * */
    @Override
    public BookDetailsData updateBookQuantity (int bookId, int bookQuantity){
        BookDetailsData book = this.getBookById(bookId);
        book.setBookQuantity(bookQuantity);
        return bookRepo.save(book);
    }

    /**
     * list of book in descending order
     * */

    @Override
    public List<BookDetailsData> sortBooksDesc() {

        return bookRepo.sortBooksByDesc();
    }

    /**
     * list of book in ascending order
     * */

    @Override
    public List<BookDetailsData> sortBooksAsc() {

        return bookRepo.sortBooksByAsc();
    }
}
