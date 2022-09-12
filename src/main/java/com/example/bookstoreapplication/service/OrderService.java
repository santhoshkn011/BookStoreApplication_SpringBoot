package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrderDTO;
import com.example.bookstoreapplication.exception.CartException;
import com.example.bookstoreapplication.exception.OrderException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.Orders;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repository.BookRepo;
import com.example.bookstoreapplication.repository.OrderRepo;
import com.example.bookstoreapplication.repository.UserRepo;
import com.example.bookstoreapplication.utility.EmailSenderService;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService{
    @Autowired
    UserRepo userRepo;
    @Autowired
    BookRepo bookRepo;
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    EmailSenderService emailSender;
    @Override
    public String addOrderDetails(OrderDTO orderDTO) {
        Optional<UserDetails> userDetails = userRepo.findById(orderDTO.getUserId());
        Optional<Book> bookDetails = bookRepo.findById(orderDTO.getBookId());
        if(userDetails.isPresent() && bookDetails.isPresent()){
            //Calculations
            double orderPrice = bookDetails.get().getPrice()*orderDTO.getOrderQuantity();
            String address = userDetails.get().getAddress();
            LocalDate orderDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
            boolean cancel = false;

            Orders orderDetails = new Orders(userDetails.get(),bookDetails.get(),orderDTO.getOrderQuantity(),orderPrice,address,orderDate,cancel);
            //Token Generated
            String token = tokenUtility.createToken(userDetails.get().getUserId());
            orderRepo.save(orderDetails);
            //sending email
            emailSender.sendEmail(userDetails.get().getEmailAddress(), "Order Placed!!!", "Please Click on the below link for the order details."+"\n"+"http://localhost:9090/orders/getOrder/"+token);
            return token;
        }else
            throw new OrderException("Invalid User ID | Book ID");
    }
    //Get Order Details by Order ID
    @Override
    public Orders getOrderDetailsByOrderId(Long orderId) {
        Optional<Orders> orderDetails = orderRepo.findById(orderId);
        if(orderDetails.isPresent()){
            return orderDetails.get();
        }else
            throw new CartException("Order ID does not exist: Invalid ID");
    }
    //Get Order Details by User ID
    @Override
    public List<Orders> getOrderDetailsByUserId(Long userId) {
        List<Orders> ordersList = orderRepo.getOrderListWithUserId(userId);
        if(ordersList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return ordersList;
    }
    //Get Order Details by Token
    @Override
    public List<Orders> getOrderDetailsByToken(String token) {
        Long userId = tokenUtility.decodeToken(token);
        List<Orders> ordersList = orderRepo.getOrderListWithUserId(userId);
        if(ordersList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return ordersList;
    }
}
