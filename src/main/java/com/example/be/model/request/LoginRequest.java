package com.example.be.model.request;

import lombok.Data;

@Data
public class LoginRequest {
    String phone;
    String password;
}
