package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.CartDTO;
import com.example.bookstoreapplication.exception.BookException;
import com.example.bookstoreapplication.exception.CartException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.Cart;
import com.example.bookstoreapplication.model.UserDetails;
import com.example.bookstoreapplication.repository.BookRepo;
import com.example.bookstoreapplication.repository.CartRepo;
import com.example.bookstoreapplication.repository.UserRepo;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class CartService implements ICartService{
    @Autowired
    CartRepo cartRepo;
    @Autowired
    TokenUtility tokenUtility;
    @Autowired
    UserRepo userRepo;
    @Autowired
    BookRepo bookRepo;

    //Adding Cart Data with userId
    @Override
    public Cart addCartData(Long userId, CartDTO cartDTO){
        Optional<UserDetails> userData = userRepo.findById(userId);
        Optional<Book> bookData = bookRepo.findById(cartDTO.getBookId());
        if(userData.isPresent()){
            if(bookData.isPresent()){
                if(cartDTO.getQuantity()<=bookData.get().getQuantity()){
                    Cart cartDetails = new Cart(userData.get(), bookData.get(), cartDTO.getQuantity());
                    return cartRepo.save(cartDetails);
                }else
                    throw new CartException("Quantity Exceeds, Available Book Quantity: "+bookData.get().getQuantity());

            }else
                throw new BookException("Book Does not exist: Invalid BookId");
        }else
            throw new CartException("User Does not exist: Invalid UserId");
    }
    //Get All cart List
    @Override
    public List<Cart> allCartList() {
        List<Cart> cartList = cartRepo.findAll();
        if(cartList.isEmpty()){
            throw new CartException("No Items added in cart yet!!");
        }else
            return cartList;
    }
    //Get Cart Data By cart ID
    @Override
    public Cart getCartDetailsByCartId(Long cartId) {
        Optional<Cart> cartDetails = cartRepo.findById(cartId);
        if(cartDetails.isPresent()){
            return cartDetails.get();
        }else
            throw new CartException("Cart ID does not exist: Invalid ID");
    }
    //Get Cart details by UserID
    @Override
    public List<Cart> getCartDetailsByUserId(Long userId) {
        List<Cart> userCartList = cartRepo.getCartListWithUserId(userId);
        if(userCartList.isEmpty()){
            throw new CartException("Cart is Empty!");
        }else
            return userCartList;
    }
    //Edit Cart details(Book and quantity) with Cart ID
    @Override
    public String editCartByCartId(Long cartId, CartDTO cartDTO) {
        Optional<Cart> cartDetails = cartRepo.findById(cartId);
        Optional<Book> bookDetails = bookRepo.findById(cartDTO.getBookId());
        if(cartDetails.isPresent()){
            if(bookDetails.isPresent()){
                cartDetails.get().setBook(bookDetails.get());
                if(cartDTO.getQuantity()<=bookDetails.get().getQuantity()){
                    cartDetails.get().setQuantity(cartDTO.getQuantity());
                    cartRepo.save(cartDetails.get());
                    return "Cart Details Updated! with Book ID: "+cartDTO.getBookId()+", Quantity: "+cartDTO.getQuantity();
                }else
                    throw new CartException("Quantity Exceeds, Available Book Quantity: "+bookDetails.get().getQuantity());
            }else
                throw new CartException("Book ID does not exist: Invalid Book ID");
        }else
            throw new CartException("Invalid Cart ID");
    }
    //Delete by Cart ID
    @Override
    public String deleteCartByCartId(Long cartId) {
        Optional<Cart> cartDetails = cartRepo.findById(cartId);
        if(cartDetails.isEmpty()){
            throw new CartException("Cart Does not found: Invalid Cart ID.");
        }else {
            cartRepo.deleteByCartId(cartId);
            return "Deleted Cart ID: "+cartId;
        }
    }
}
