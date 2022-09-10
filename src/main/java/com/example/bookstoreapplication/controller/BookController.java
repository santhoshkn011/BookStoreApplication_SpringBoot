package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.dto.QuantityDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.service.IBookService;
import com.example.bookstoreapplication.utility.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    IBookService bookService;
    @Autowired
    TokenUtility tokenUtility;
    //Home Page
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String homePage() {
        return "Hello! This is Book Store Application Home Page";
    }
    //Inserting Data
    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> addBookDetails(@Valid @RequestBody BookDTO bookDTO){
        String response = bookService.addBookDetails(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book Details Added", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
    //Get all Book Details
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllBookDetails(BookDTO bookDTO){
        List<Book> bookList = bookService.allBookDetails(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("All Book Details, total count: "+bookList.size(), bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Get the book details by Book ID
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseDTO> getBookDataById(@PathVariable Long id) {
        Book bookDetails = bookService.getBookDataById(id);
        ResponseDTO responseDTO = new ResponseDTO("Book details with ID: "+id,bookDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Delete book details by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteBookDataByID(@PathVariable Long id) {
        Book deletedData = bookService.deleteData(id);
        ResponseDTO respDTO= new ResponseDTO("Deleted Successfully, Below Data is deleted", deletedData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Search by Book
    @GetMapping("/data/{bookName}")
    public ResponseEntity<ResponseDTO> getBookDataById(@PathVariable String bookName) {
        Book bookDetails = bookService.getBookDataByBookName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Book details: ",bookDetails);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Update by Book ID
    @PutMapping("/updateData/{id}")
    public ResponseEntity<ResponseDTO> updateUserByEmailAddress(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        Book bookData = bookService.updateDataById(bookDTO, id);
        ResponseDTO respDTO= new ResponseDTO("Data Update info", bookData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Sorting in Ascending order by price
    @GetMapping("/sort/ascendingPrice")
    public ResponseEntity<ResponseDTO> sortingAscending(){
        List<Book> bookList = bookService.sortAscendingByPrice();
        ResponseDTO responseDTO = new ResponseDTO("Sorted by Price in Ascending order", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //Sorting in Descending order by price
    @GetMapping("/sort/descendingPrice")
    public ResponseEntity<ResponseDTO> sortingDescending(){
        List<Book> bookList = bookService.sortDescendingByPrice();
        ResponseDTO responseDTO = new ResponseDTO("Sorted by Price in Ascending order", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    //update quantity by Book ID
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<ResponseDTO> updateQuantityById(@PathVariable Long id,@Valid @RequestBody QuantityDTO quantityDTO) {
        String response = bookService.updateQuantityById(quantityDTO, id);
        ResponseDTO respDTO= new ResponseDTO("Data Update info", response);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
}
