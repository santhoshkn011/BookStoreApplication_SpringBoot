package com.example.bookstoreapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public @ToString class LoginDTO {
    private String emailAddress;
    private String password;
}
