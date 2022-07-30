package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.CartData;

import java.util.List;
import java.util.Optional;

public interface ICartService {

    public List<CartData> getCartDetails();

    Optional<CartData> getCartDetailsById(Integer cartId);

    Optional<CartData> deleteCartItemById(Integer cartId);

    CartData updateRecordById(Integer cartId, CartDTO cartDto);

    CartData insertItems(String token,CartDTO cartdto);
    
    public List<CartData> deleteAllFromCart();

    public CartData decreaseQuantity(Integer cartId);

    public CartData increaseQuantity(Integer cartId);

}