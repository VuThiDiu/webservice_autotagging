package com.example.webservice.dto;


import com.google.api.client.util.DateTime;
import lombok.Data;

@Data

public class UploadProduct {
    private String userId;
    private int quantityInStock;
    private int price;
    private DateTime dateTime;
    private int discount;
    private String address;
    private String description;
    private String title;
}
