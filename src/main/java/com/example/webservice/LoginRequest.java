package com.example.webservice;


import lombok.Data;

@Data
public class LoginRequest {
    private  String userName;
    private String password;
}
