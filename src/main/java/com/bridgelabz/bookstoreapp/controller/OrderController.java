package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.OrderData;
import com.bridgelabz.bookstoreapp.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController  {

    /**
     * Autowired IOrderService to inject its dependency here
     */
    @Autowired
    IOrderService orderService;

    /**
     * Ability to call api to insert order record
     *@param: OrderDTO
     *@return : ResponseEntity of insertOrder
     */
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertOrder(@RequestBody OrderDTO orderdto){
        OrderData newOrder = orderService.insertOrder(orderdto);
        ResponseDTO dto = new ResponseDTO("Order place successfully !",newOrder);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    /**
     * Ability to call api to retrive all record
     *@param: none
     *@return : ResponseEntity of getAllOrderRecords
     */
    @GetMapping("/retrieveAllOrders")
    public ResponseEntity<ResponseDTO> getAllOrderRecords(){
        List<OrderData> newOrder = orderService.getAllOrders();
        ResponseDTO dto = new ResponseDTO("All records retrieved successfully !",newOrder);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    /**
     * Ability to call api to retrive order by id record
     *@param: id
     *@return : ResponseEntity of getBookRecordById
     */
    @GetMapping("/retrieveOrder/{id}")
    public ResponseEntity<ResponseDTO> getBookRecordById(@PathVariable Integer id){
        OrderData newOrder = orderService.getOrderById(id);
        ResponseDTO dto = new ResponseDTO("Record retrieved successfully !",newOrder);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    /**
     * Ability to call api to cancel order by id record
     *@param: id
     *@return : ResponseEntity of cancelOrder
     */
    @PutMapping("/cancelOrder/{id}")
    public ResponseEntity<ResponseDTO> cancelOrder(@RequestHeader(name = "token") String token,@PathVariable int id) {
        String order=orderService.cancelOrder(token,id);
        ResponseDTO response= new ResponseDTO("id "+id,  order);
        return new ResponseEntity<> (response,HttpStatus.ACCEPTED);
    }

}