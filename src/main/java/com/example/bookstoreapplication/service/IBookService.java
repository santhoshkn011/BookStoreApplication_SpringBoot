package com.example.bookstoreapplication.service;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.dto.QuantityDTO;
import com.example.bookstoreapplication.model.Book;

import java.util.List;

public interface IBookService {
    String addBookDetails(BookDTO bookDTO);

    List<Book> allBookDetails(BookDTO bookDTO);

    Book getBookDataById(Long id);

    Book deleteData(Long id);

    Book getBookDataByBookName(String bookName);

    Book updateDataById(BookDTO bookDTO, Long id);

    List<Book> sortAscendingByPrice();

    List<Book> sortDescendingByPrice();

    String updateQuantityById(QuantityDTO quantityDTO, Long id);
}
