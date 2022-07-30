package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.exception.CartException;
import com.bridgelabz.bookstoreapp.exception.UserRegistrationException;
import com.bridgelabz.bookstoreapp.model.BookDetailsData;
import com.bridgelabz.bookstoreapp.model.CartData;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;
import com.bridgelabz.bookstoreapp.repository.BookDetailsRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class CartService implements ICartService {

    /**
     * Autowired interfaces to inject its dependency here
     */
    @Autowired
    BookDetailsRepository bookStoreRepository;
    @Autowired
    UserRegistrationRepository userRegistrationRepository;
    @Autowired
    CartRepository bookStoreCartRepository;


    @Autowired
    TokenUtil tokenUtil;

    /**
     * created a method name as insertItems
     * Ability to save cart details to repository
     * */

    @Override
    public CartData insertItems(String token ,CartDTO cartdto) {
        Optional<BookDetailsData> book = bookStoreRepository.findById(cartdto.getBookId());
        Optional<UserRegistrationData> userRegistration = userRegistrationRepository.findById(cartdto.getUserId());
        if (book.isPresent() && userRegistration.isPresent()) {
            CartData newCart = new CartData(cartdto.getQuantity(), book.get(), userRegistration.get());
            bookStoreCartRepository.save(newCart);
            return newCart;
        } else {
            throw new UserRegistrationException("Book or User does not exists");
        }
    }
    /**
     * created a method name as getCartDetails
     * - Ability to get all cart' data by findAll() method
     * */
    @Override
    public List<CartData> getCartDetails() {
        List<CartData> cart = bookStoreCartRepository.findAll();
        return cart;
    }
    
    // public List<CartData> getCartDetails() {
    //     List<CartData> getCartDetails=bookStoreCartRepository.findAll();
    //     ResponseDTO dto= new ResponseDTO();
    //     if (getCartDetails.isEmpty()){
    //         String   message=" Not found Any Cart details ";
    //         dto.setMessage(message);
    //         dto.setData(0);
    //         return dto;

    //     }
    //     else {
    //         dto.setMessage("the list of cart items is sucessfully retrived");
    //         dto.setData(getCartDetails);
    //         return dto;
    //     }
    // }
    /**
     * created a method name as getCartDetailsById
     * - Ability to get cart data by cartId*/
    @Override
    public Optional<CartData> getCartDetailsById(Integer cartId) {
        Optional<CartData> getCartData=bookStoreCartRepository.findById(cartId);
        if (getCartData.isPresent()){
            return getCartData;
        }
        else {
            throw new UserRegistrationException(" Didn't find any record for this particular cartId");
        }
    }
    /**
     * created a method name as deleteCartItemById
     * ability to delete data by particular cart id
     * */
    @Override
    public Optional<CartData> deleteCartItemById(Integer cartId) {
        Optional<CartData> deleteData=bookStoreCartRepository.findById(cartId);
        if (deleteData.isPresent()){
            bookStoreCartRepository.deleteById(cartId);
            return deleteData;
        }
        else {
            throw new UserRegistrationException(" Did not get any cart for specific cart id ");
        }

    }
    /**
     * created a method name as updateRecordById
     * Ability to update cart data for particular id
     * */
    @Override
    public CartData updateRecordById(Integer cartId, CartDTO cartDTO) {
        Optional<CartData> cart = bookStoreCartRepository.findById(cartId);
        Optional<BookDetailsData> book = bookStoreRepository.findById(cartDTO.getBookId());
        Optional<UserRegistrationData> user = userRegistrationRepository.findById(cartDTO.getUserId());
        if(cart.isPresent()) {
            if(book.isPresent() && user.isPresent()) {
                CartData newCart = new CartData(cartDTO.getQuantity(),book.get(),user.get());
                bookStoreCartRepository.save(newCart);
                log.info("Cart record updated successfully for id "+cartId);
                return newCart;
            }
            else {
                throw new UserRegistrationException("Book or User doesn't exists");
            }
        }
        else {
            throw new UserRegistrationException(("Cart Record doesn't exists"));
        }

    }
    @Override
    public List<CartData> deleteAllFromCart() {
        bookStoreCartRepository.deleteAll();
        return bookStoreCartRepository.findAll();
    }
    @Override
    public CartData decreaseQuantity(Integer cartId) {
        Optional<CartData> cart = bookStoreCartRepository.findById(cartId);
        Optional<BookDetailsData> book = bookStoreRepository.findById(cart.get().getBook().getBookId());

        if (cart.get().getQuantity() > 0) {
            cart.get().setQuantity(cart.get().getQuantity() - 1);
            bookStoreCartRepository.save(cart.get());
            log.info("Quantity in cart record updated successfully");
            book.get().setBookQuantity(book.get().getBookQuantity() + 1);
            bookStoreRepository.save(book.get());
            return cart.get();
        } else {
            throw new CartException("Cart is empty");
        }
    }
    @Override
    public CartData increaseQuantity(Integer cartId) {
        Optional<CartData> cart = bookStoreCartRepository.findById(cartId);
        Optional<BookDetailsData> book = bookStoreRepository.findById(cart.get().getBook().getBookId());
        if (book.get().getBookQuantity() >= 1) {
            cart.get().setQuantity(cart.get().getQuantity() + 1);
            bookStoreCartRepository.save(cart.get());
            log.info("Quantity in cart record updated successfully");
            book.get().setBookQuantity(book.get().getBookQuantity() - 1);
            bookStoreRepository.save(book.get());
            return cart.get();
            } else {
                throw new CartException("Requested quantity is not available");
            }
        }
   
}

