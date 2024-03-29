package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.CartData;
import com.bridgelabz.bookstoreapp.services.ICartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/api/cart")
public class CartController {

    /**
     * Autowired ICartService to inject its dependency here
     */

    @Autowired
    ICartService cartService;

    /**
     * Ability to call api to insert all cart records
     *@param: CartDTO
     *@return : ResponseEntity of insertItem to cart
     */
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> insertItem(@RequestHeader(name = "token") String token, @Valid @RequestBody CartDTO cartdto) {
        CartData newCart = cartService.insertItems(token,cartdto);
        ResponseDTO responseDTO = new ResponseDTO("User registered successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    /**
     * Ability to call api to retrieve all cart records
     *@param: none
     *@return : ResponseEntity of getAllCartDetails
     */
    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllCartDetails() {
        List<CartData> newCart = cartService.getCartDetails();
        ResponseDTO responseDTO = new ResponseDTO("All records retrieved successfully !", newCart);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }


    /**
     * Ability to call api to retrieve cart record by cartId
     *@param: cartId
     *@return : ResponseEntity of getCartDetailsById
     */
    @GetMapping("/getById/{cartId}")
    public ResponseEntity<ResponseDTO> getCartDetailsById(@PathVariable Integer cartId){
        Optional<CartData> specificCartDetail=cartService.getCartDetailsById(cartId);
        ResponseDTO responseDTO=new ResponseDTO("Cart details retrieved successfully",specificCartDetail);
        return new ResponseEntity(responseDTO,HttpStatus.ACCEPTED);
    }

    /**
     * Ability to call api to update cart by id
     *@param: cartId
     *@return : ResponseEntity of updateCartById
     */
    @PutMapping("/updateById/{cartId}")
    public ResponseEntity<ResponseDTO> updateCartById(@PathVariable Integer cartId,@Valid @RequestBody CartDTO cartDto){
        CartData updateRecord = cartService.updateRecordById(cartId,cartDto);
        ResponseDTO dto = new ResponseDTO(" Cart Record updated successfully by Id",updateRecord);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }

    /**
     * Ability to call api to delete cart by id
     *@param: cartId
     *@return : ResponseEntity of updateCartById
     */
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<ResponseDTO> deleteCartById(@PathVariable Integer cartId) {
        Optional<CartData> delete = cartService.deleteCartItemById(cartId);
        ResponseDTO responseDTO = new ResponseDTO("Cart delete successfully", delete);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<ResponseDTO> deleteBooks() {
        List<CartData> books = cartService.deleteAllFromCart();
        return new ResponseEntity(books, HttpStatus.OK);
    }

    @GetMapping("/decreaseQuantity/{cartID}")
    public ResponseEntity<ResponseDTO> decreaseQuantity(@PathVariable Integer cartID) {
        CartData newCart = cartService.decreaseQuantity(cartID);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !", newCart);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    @GetMapping("/increaseQuantity/{cartID}")
    public ResponseEntity<ResponseDTO> increaseQuantity(@PathVariable Integer cartID) {
        CartData newCart = cartService.increaseQuantity(cartID);
        ResponseDTO dto = new ResponseDTO("Quantity for book record updated successfully !", newCart);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
}
