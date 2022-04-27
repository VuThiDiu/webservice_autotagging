package com.example.webservice.controller;
import com.example.webservice.dto.TagResponse;
import com.example.webservice.model.Image;
import com.example.webservice.service.IImageService;
import com.example.webservice.service.impl.ImageService;
import com.example.webservice.utils.LoadModel;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;



@Controller
@RequestMapping("/auto_tagging")
public class AutomateTaggingClothesController {
    @Autowired
    IImageService iImageService;
    LoadModel loadModel = new LoadModel();

    @Autowired
    ImageService imageService;


    @PostMapping(value = "/autoTaggingImage", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<TagResponse>  automateTaggingClothes(@RequestParam("file") MultipartFile file) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        Image image = new Image();
        if(file.isEmpty()){
            return new ResponseEntity<TagResponse>(new TagResponse(), HttpStatus.EXPECTATION_FAILED);
        }else{
            try{
                // generate image
                String fileName = iImageService.save(file);
                String imageUrl = iImageService.getImageUrl(fileName);
                InputStream inputStream = file.getInputStream();
                BufferedImage input = ImageIO.read(inputStream);
                image = loadModel.prediction(input);

                return new ResponseEntity<TagResponse>(new TagResponse(image.getTagCategory(), image.getTagColor()), HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<TagResponse>(new TagResponse(), HttpStatus.EXPECTATION_FAILED);
            }
        }
    }
}
