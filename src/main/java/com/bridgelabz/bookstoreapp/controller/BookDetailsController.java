package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDetailsDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.BookDetailsData;
import com.bridgelabz.bookstoreapp.services.IBookDetailsService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookservice")
@Slf4j
public class BookDetailsController {

    /**
     * Autowired IBookService to inject its dependency here
     */ 
    @Autowired
    IBookDetailsService bookInterfaceService;


    /**
     * Autowired TokenUtil to inject its dependency here
     */
    @Autowired
    private TokenUtil tokenUtil;

    /**
     * ========================================
     * All api starts here
     * ========================================
     */

    /**
     * Ability to call api to retrieve all book records
     *@param : none
     *@return : ResponseEntity of getAllBook
     */
    @GetMapping(value = {"", "/", "/getBooks"})
    public ResponseEntity<ResponseDTO> getAllBooks() {
        List<BookDetailsData> allBooks = bookInterfaceService.showAllBooks();
        ResponseDTO dto = new ResponseDTO("All Books Retrieved successfully:", allBooks);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Ability to call api to retrieve one book records By Name
     *@param : bookName
     *@return : ResponseEntity of getOneBookByName
     */
    @GetMapping("/getBookByName/{bookName}")
    public ResponseEntity<ResponseDTO> getOneBookByName(@PathVariable String bookName)
    {
        List<BookDetailsData> getOneBook = bookInterfaceService.getBookByName(bookName);
        ResponseDTO dto = new ResponseDTO("Book retrieved successfully"+bookName, getOneBook);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    /**
     * Ability to call api to retrieve book record By Id
     *@param :bookId
     *@return : ResponseEntity of getOneBook
     */
    @GetMapping("/getBook/{bookId}")
    public ResponseEntity<ResponseDTO> getOneBook(@PathVariable int bookId)
    {
        BookDetailsData getOneBook = bookInterfaceService.getBookById(bookId);
        ResponseDTO dto = new ResponseDTO("Book retrieved successfully"+bookId, getOneBook);
        return new ResponseEntity<ResponseDTO>(dto,HttpStatus.OK);
    }

    /**
     * Ability to call api to insert Book record = Add Data to repo
     *@param : BookDetailsDTO
     *@return : ResponseEntity of addBook
     */
    @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> addBook(@RequestHeader(name = "token") String token, @RequestBody BookDetailsDTO bookDto) {
        BookDetailsData addBook = bookInterfaceService.addBook(token,bookDto);
        ResponseDTO dto = new ResponseDTO("Book added successfully ",tokenUtil.createToken(addBook.getBookId()));
        return new ResponseEntity<ResponseDTO>(dto, HttpStatus.CREATED);
    }

    /**
     * Ability to call api to update book record by BookId - update record by id
     *@param : bookId,BookDetailsDTO
     *@return : ResponseEntity of updateBookData
     */
    @PutMapping("/update/{bookId}")
    public ResponseEntity<ResponseDTO> updateBookData(@PathVariable("bookId") int bookId,
                                                      @RequestBody BookDetailsDTO bookDTO) {
        BookDetailsData updateBook = bookInterfaceService.updateBook(bookId, bookDTO);
        log.debug(" After Update " + updateBook.toString());
        ResponseDTO response = new ResponseDTO("Updated  for" + bookId, updateBook);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    /**
     * Ability to call api to delete book record by BookId
     *@param : bookId
     *@return : ResponseEntity of deleteBook
     */
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<ResponseDTO> deleteBook(@PathVariable("bookId") int bookId) {
        bookInterfaceService.deleteBook(bookId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + bookId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);

    }

    @PutMapping("/updatequantity/{bookId}/{bookQuantity}")
    public ResponseEntity<ResponseDTO> updateBookQuantity(@PathVariable int bookId, @PathVariable int bookQuantity) {
        BookDetailsData updateBookQuantity = bookInterfaceService.updateBookQuantity(bookId, bookQuantity);
        ResponseDTO response = new ResponseDTO("Book Quantity Update is success for id " + bookId, updateBookQuantity);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    // to sort book records in ascending order
    @GetMapping("/sortasc")
    public ResponseEntity<ResponseDTO> sortBooksAsc() {
        List<BookDetailsData> book = null;
        book = bookInterfaceService.sortBooksAsc();
        ResponseDTO response = new ResponseDTO("Books in ascending order :", book);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    //to sort book records in descending order
    @GetMapping("/sortdesc")
    public ResponseEntity<ResponseDTO> sortBooksDesc() {
        List<BookDetailsData> book = null;
        book = bookInterfaceService.sortBooksDesc();
        ResponseDTO response = new ResponseDTO("Books in  descending order :", book);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    }
