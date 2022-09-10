package com.example.bookstoreapplication.controller;

import com.example.bookstoreapplication.dto.BookDTO;
import com.example.bookstoreapplication.dto.ResponseDTO;
import com.example.bookstoreapplication.dto.UserDTO;
import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.UserDetails;
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
        Book response = bookService.addBookDetails(bookDTO);
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
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateUserByEmailAddress(@PathVariable Long id,@Valid @RequestBody BookDTO bookDTO) {
        Book bookData = bookService.updateDataById(bookDTO, id);
        ResponseDTO respDTO= new ResponseDTO("Data Update info", bookData);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    //Sorting the book details by price
//    @GetMapping("/sortByPrice/ascending")
//    public ResponseEntity<ResponseDTO> sortByPrice(){
//        List<Book> bookList = bookService.sortBookDetailsByPrice();
//        ResponseDTO responseDTO = new ResponseDTO("Sorted by Price in Ascending order", bookList);
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }
}
