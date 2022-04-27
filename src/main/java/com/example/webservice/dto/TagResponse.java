package com.example.webservice.dto;


import lombok.Data;

@Data
public class TagResponse {
    private String tagCategory;
    private String tagColor;
    public TagResponse() {
    }

    public TagResponse(String tagCategory, String tagColor) {
        this.tagCategory = tagCategory;
        this.tagColor = tagColor;
    }
}
