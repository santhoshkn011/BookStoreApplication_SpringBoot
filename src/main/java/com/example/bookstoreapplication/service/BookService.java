package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.exception.BookException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.BookRepo;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    BookRepo bookRepo;
    @Autowired
    TokenUtility tokenUtility;
    //Adding book details
    @Override
    public Book addBookDetails(BookDTO bookDTO) {
    Book bookDetails = new Book(bookDTO);
    bookRepo.save(bookDetails);
    //String token = tokenUtility.createToken(bookDetails.getBookId());
    return bookDetails;
    }
    //get all book details
    @Override
    public List<Book> allBookDetails(BookDTO bookDTO) {
        List<Book> bookList = bookRepo.findAll();
        if(bookList.isEmpty()){
            throw new BookException("No Books Added yet!");
        }else
            return bookList;
    }
    //get book details by id
    @Override
    public Book getBookDataById(Long id) {
        Book bookDetails = bookRepo.findById(id).orElse(null);
        if(bookDetails != null){
            return bookDetails;
        }else
            throw new BookException("ID: "+id+" is not available");
    }

    //delete by id
    @Override
    public Book deleteData(Long id) {
        Book bookDetails = bookRepo.findById(id).orElse(null);
        if (bookDetails != null) {
            bookRepo.deleteById(id);
        } else
            throw new BookException("Invalid Id");
        return bookDetails;
    }

    //Get Book Data by Book Name
    @Override
    public Book getBookDataByBookName(String bookName) {
        Book bookDetails = bookRepo.findByBookName(bookName);
        if(bookDetails == null){
            throw new BookException("Book Name: "+bookName+" is not available");
        }else
            return bookDetails;
    }
    //update book data by email address
    @Override
    public Book updateDataById(BookDTO bookDTO, Long id) {
        Book bookDetails = bookRepo.findById(id).orElse(null);
        if(bookDetails != null){
            bookDetails.setBookName(bookDTO.getBookName());
            bookDetails.setAuthorName(bookDTO.getAuthorName());
            bookDetails.setBookDescription(bookDTO.getBookDescription());
            bookDetails.setBookImage(bookDetails.getBookImage());
            bookDetails.setPrice(bookDTO.getPrice());
            bookDetails.setQuantity(bookDTO.getQuantity());
            return bookRepo.save(bookDetails);
        }else
            throw new BookException("Invalid ID: "+id);
    }
    //Sorting by Array.sort() method
//    @Override
//    public List<Book> sortBookDetailsByPrice() {
//        List<Book> listOfBooks = bookRepo.findAll();
//        if(listOfBooks.isEmpty()){
//            throw new BookException("No Books added in the list yet!!!");
//        }else
//            return null;
//    }
}
