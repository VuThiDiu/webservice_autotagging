package com.example.webservice.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "image")
public class Image {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String id;

    private String urlImage;

    private String tagCategory;

    private String tagColor;

    public Image(String urlImage, String tagCategory, String tagColor) {
        this.urlImage = urlImage;
        this.tagCategory = tagCategory;
        this.tagColor = tagColor;
    }

    public Image() {
    }
}
