package com.example.bookstoreapplication.dto;

import com.example.bookstoreapplication.model.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    String message;
    Object response;
    public ResponseDTO(String message, UserDetails response) {
        this.message = message;
        this.response = response;
    }
    public ResponseDTO(String message, String response) {
        this.message = message;
        this.response = response;
    }
}
