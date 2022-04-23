package com.example.webservice.dto;


import lombok.Data;

@Data
public class TagResponse {
    private String tagCategory;
    private String tagColor;

    private String imageURL;
    public TagResponse() {
    }

    public TagResponse(String tagCategory, String tagColor, String imageURL) {
        this.imageURL = imageURL;
        this.tagCategory = tagCategory;
        this.tagColor = tagColor;
    }
}
