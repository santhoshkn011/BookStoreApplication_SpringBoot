package com.example.bookstoreapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    public String email_address;
    public String password;
}
