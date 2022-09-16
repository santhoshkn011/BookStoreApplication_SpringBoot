package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.OrdersDTO;
import com.example.bookstoreapplication.exception.OrderException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.BookOrders;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repository.BookOrdersRepo;
import com.example.bookstoreapplication.repository.CartRepo;
import com.example.bookstoreapplication.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService implements IOrdersService{
    ArrayList<BookOrders> orderList = new ArrayList<>();
    @Autowired
    UserRepo userRepo;
    @Autowired
    CartRepo cartRepo;
    @Autowired
    BookOrdersRepo bookOrdersRepo;
    @Override
    public BookOrders addOrderDetails(OrdersDTO ordersDTO) {
        Optional<UserDetails> userDetails = userRepo.findById(ordersDTO.getUserId());
        List<Cart> cartList = cartRepo.getCartListWithUserId(ordersDTO.getUserId());
        double totalOrderPrice = 0;
        int totalOrderQuantity = 0;
        List<Book> orderedBooks = new ArrayList<>();
        String address = "";
        for (int i=0; i<cartList.size(); i++){
            totalOrderPrice += cartList.get(i).getBook().getPrice();
            totalOrderQuantity += cartList.get(i).getQuantity();
            orderedBooks.add(cartList.get(i).getBook());
        }
        if(userDetails.isPresent() && !cartList.isEmpty()) {
            if(ordersDTO.getAddress().isEmpty()) {
                address = userDetails.get().getAddress();
            }else {
                address = ordersDTO.getAddress();
            }
            BookOrders orderDetails = new BookOrders(userDetails.get(), orderedBooks, totalOrderQuantity, totalOrderPrice, address, LocalDate.now(), false);
            orderList.add(orderDetails);
            bookOrdersRepo.save(orderDetails);
            for (int i=0; i<cartList.size(); i++) {
                Book book = cartList.get(i).getBook();
                int updatedQty = this.updateBookQty(book.getQuantity(), cartList.get(i).getQuantity());
                book.setQuantity(updatedQty);
                cartRepo.deleteByCartId(cartList.get(i).getCartId());
            }
            return orderDetails;
        } else{
            throw new OrderException("Invalid User ID | Cart is empty");
        }
    }
    //Method to update book Quantities after placing an order
    private int updateBookQty(int bookQty, int bookQtyInCart){
        int updatedQty = bookQty - bookQtyInCart;
        if (updatedQty <= 0)
            return 0;
        else
            return updatedQty;
    }
    //Get all orders list of user ID.
    @Override
    public List<BookOrders> getAllOrderItemsOfUserId(Long userId) {
        List<BookOrders> ordersList = bookOrdersRepo.findAllById(userId);
        if(ordersList.isEmpty())
            throw new OrderException("Invalid User ID or no orders placed");
        else
            return ordersList;
    }
}
