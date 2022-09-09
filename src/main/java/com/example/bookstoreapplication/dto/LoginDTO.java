package com.example.bookstoreapplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
public @ToString class LoginDTO {
    public String emailAddress;
    public String password;
}
