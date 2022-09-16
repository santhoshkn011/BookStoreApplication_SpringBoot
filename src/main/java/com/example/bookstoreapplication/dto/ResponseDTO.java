package com.example.bookstoreapplication.dto;

import com.example.bookstoreapplication.model.Book;
import com.example.bookstoreapplication.model.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object response;
    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, Optional<UserDetails> response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, List<UserDetails> response) {
        this.message = message;
        this.response = response;
    }

//    public ResponseDTO(String message, Optional<UserDetails> response) {
//        this.message = message;
//        this.response = response;
//    }
    public ResponseDTO(String message, Book response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, Object response) {
        this.message = message;
        this.response = response;
    }
}