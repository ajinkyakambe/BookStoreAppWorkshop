package com.bridgelabz.bookstoreapp.services;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.exception.UserRegistrationException;
import com.bridgelabz.bookstoreapp.model.BookDetailsData;
import com.bridgelabz.bookstoreapp.model.OrderData;
import com.bridgelabz.bookstoreapp.model.UserRegistrationData;
import com.bridgelabz.bookstoreapp.repository.BookDetailsRepository;
import com.bridgelabz.bookstoreapp.repository.OrderRepository;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import com.bridgelabz.bookstoreapp.util.EmailSMTP;
import com.bridgelabz.bookstoreapp.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {

    /**
     * Dependeny injection for all three repository
     * */

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private BookDetailsRepository bookRepo;
    @Autowired
    private UserRegistrationRepository userRepo;

    /**
     * Dependeny injection for Token generation and email service
     * */
    @Autowired
    TokenUtil tokenUtil;
    @Autowired
    EmailSMTP mailService;


orderdto.map(itemsingle)


    @Override
    public OrderData insertOrder(OrderDTO orderdto) {
        System.out.println(orderdto);
       
        ArrayList<BookDetailsData> bookList = new ArrayList<>();
        Optional<BookDetailsData> book = bookRepo.findById(orderdto.getBookId());
        Optional<UserRegistrationData> user = userRepo.findById(orderdto.getUserId());
        if (book.isPresent() && user.isPresent()) {
            int totalPrice = book.get().getBookPrice() * orderdto.getQuantity();
            List<String> nameList = bookList.stream().map(BookDetailsData::getBookName).toList();
            OrderData newOrder = new OrderData(totalPrice, orderdto.getQuantity(), orderdto.getAddress(), book.get(), user.get());
            orderRepo.save(newOrder);
            log.info("Order record inserted successfully");
            String token = tokenUtil.createToken(newOrder.getId());
            // mailService.sendEmail("ajinkyakambe@gmail.com", "Order Details", "Order Confirmed,\nOrderId- "
            //         + newOrder.getId() + "\nOrder placed on - " + orderdto.getOrderDate() + " for bookId - " + orderdto.getBookId() +".\nShipping to address - "+ orderdto.getAddress()
            //         +  ".\nTotal price need to pay " + totalPrice +"\nBook Names - " + nameList);
            // return token;
            return newOrder;
        } else {
            throw new UserRegistrationException("Requested quantity is out of stock");
        }
    }

    @Override
    public List<OrderData> getAllOrders() {
        List<OrderData> orderList = orderRepo.findAll();
        log.info("ALL order records retrieved successfully");
        return orderList;
    }

    @Override
    public OrderData getOrderById(Integer id) {
        Optional<OrderData> order = orderRepo.findById(id);
        if (order.isEmpty()) {
            throw new UserRegistrationException("Order Record doesn't exists");
        } else {
            log.info("Order record retrieved successfully for id " + id);
            return order.get();
        }
    }


    @Override
    public String cancelOrder(String token,int orderId) {
        int id = Math.toIntExact(tokenUtil.decodeToken(token));
        Optional<UserRegistrationData> isPresent = userRepo.findById(id);
        if (isPresent.isPresent()) {
            Optional<OrderData> order = orderRepo.findById(orderId);
            if (order.isPresent()) {
                order.get().setCancel(true);
                orderRepo.save(order.get());
                mailService.sendEmail("ajinkyakambe@gmail.com", "Order Cancellation", "Order Cancelled,\nOrderId- "
                        + orderId);
                return "Cancel order Successful";
            }
            throw new UserRegistrationException("OrderId  doesn't exists");
        }
        return  "cancel order not successful";
    }


}

