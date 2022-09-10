package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.dto.QuantityDTO;
import com.example.bookstoreapplication.exception.BookException;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.repository.BookRepo;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements IBookService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    TokenUtility tokenUtility;

    //Adding book details
    @Override
    public String addBookDetails(BookDTO bookDTO) {
        Book bookDetails = new Book(bookDTO);
        bookRepo.save(bookDetails);
        String token = tokenUtility.createToken(bookDetails.getBookId());

        //String token = tokenUtility.createToken(bookDetails.getBookId());
        return token;
    }

    //get all book details
    @Override
    public List<Book> allBookDetails(BookDTO bookDTO) {
        List<Book> bookList = bookRepo.findAll();
        if (bookList.isEmpty()) {
            throw new BookException("No Books Added yet!");
        } else
            return bookList;
    }

    //get book details by id
    @Override
    public Book getBookDataById(Long id) {
        Book bookDetails = bookRepo.findById(id).orElse(null);
        if (bookDetails != null) {
            return bookDetails;
        } else
            throw new BookException("ID: " + id + " is not available");
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
        if (bookDetails == null) {
            throw new BookException("Book Name: " + bookName + " is not available");
        } else
            return bookDetails;
    }

    //update book data by email address
    @Override
    public Book updateDataById(BookDTO bookDTO, Long id) {
        Book bookDetails = bookRepo.findById(id).orElse(null);
        if (bookDetails != null) {
            bookDetails.setBookName(bookDTO.getBookName());
            bookDetails.setAuthorName(bookDTO.getAuthorName());
            bookDetails.setBookDescription(bookDTO.getBookDescription());
            bookDetails.setBookImage(bookDetails.getBookImage());
            bookDetails.setPrice(bookDTO.getPrice());
            bookDetails.setQuantity(bookDTO.getQuantity());
            return bookRepo.save(bookDetails);
        } else
            throw new BookException("Invalid ID: " + id);
    }

    //Sorting : Ascending
    @Override
    public List<Book> sortAscendingByPrice() {
        List<Book> listOfBooks = bookRepo.findAll();
        //sorting the list in ascending order by price
        List<Book> bookList = listOfBooks.stream()
                .sorted(Comparator.comparingInt(Book::getPrice))
                .collect(Collectors.toList());
        if (bookList.isEmpty()) {
            throw new BookException("No Books added in the list yet!!!");
        } else
            return bookList;
    }

    //Sorting : Descending
    @Override
    public List<Book> sortDescendingByPrice() {
        List<Book> listOfBooks = bookRepo.findAll();
        //sorting the list in descending order by price
        List<Book> bookList = listOfBooks.stream()
                .sorted(Comparator.comparingInt(Book::getPrice).reversed())
                .collect(Collectors.toList());
        if (bookList.isEmpty()) {
            throw new BookException("No Books added in the list yet!!!");
        } else
            return bookList;
    }

    //update Quantity by ID
    @Override
    public String updateQuantityById(QuantityDTO quantityDTO, Long id) {
        Optional<Book> bookDetails = bookRepo.findById(id);
        String token = tokenUtility.createToken(bookDetails.get().getBookId());
        if (bookDetails.isPresent()) {
            if (quantityDTO.getToken().equals(token)) {
                bookDetails.get().setQuantity(quantityDTO.getQuantity());
                bookRepo.save(bookDetails.get());
                return "Updated Quantity";
            } else
                throw new BookException("Token Does not match");

        } else
            throw new BookException("Invalid ID");
    }
}
