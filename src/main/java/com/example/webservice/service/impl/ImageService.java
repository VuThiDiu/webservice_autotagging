package com.example.webservice.service.impl;
import com.example.webservice.model.Image;
import com.example.webservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;

    public Image saveImage(Image image){
        return imageRepository.save(image);
    }
}
