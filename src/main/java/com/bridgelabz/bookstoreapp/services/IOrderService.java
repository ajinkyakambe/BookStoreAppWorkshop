package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.model.OrderData;

import java.util.List;

public interface IOrderService {
    public OrderData insertOrder(OrderDTO orderdto);

    public List<OrderData> getAllOrders();

    public OrderData getOrderById(Integer id);

    String cancelOrder( String token,int orderId);
}
