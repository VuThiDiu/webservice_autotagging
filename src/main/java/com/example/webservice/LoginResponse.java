package com.example.webservice;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String id;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String accessToken, String id, String username) {
        this.accessToken = accessToken;
        this.username = username;
        this.id=id;
    }
}
