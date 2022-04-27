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
    private String tagCategory;

    private String tagColor;

    public Image( String tagCategory, String tagColor) {
        this.tagCategory = tagCategory;
        this.tagColor = tagColor;
    }

    public Image() {
    }
}
