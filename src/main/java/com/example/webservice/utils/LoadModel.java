package com.example.webservice.utils;

import com.example.webservice.common.Config;
import com.example.webservice.dto.TagResponse;
import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.example.webservice.model.Image;


public class LoadModel {
    public List<String> tagCategory = Arrays.asList("Quần Dài", "Quần Short", "Váy Liền", "Áo Phông", "Áo Sơ Mi", "Áo Nỉ", "Áo Khoác");
    public List<String> tagColor = Arrays.asList("trắng", "hồng", "đỏ", "vàng", "xanh dương", "xám", "đen", "xanh lá");

    public Image prediction(BufferedImage image) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        int predictionForCategory = predictionForCategory(image);
        int predictionForColor = predictionForColor(image);
        return new Image(tagCategory.get(predictionForCategory), tagColor.get(predictionForColor));

    }
    public int  predictionForCategory(BufferedImage image) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String clothesClassification = new ClassPathResource(
                "/static/category.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(clothesClassification );
        ImageLoader loader = new ImageLoader(Config.IMAGE_HEIGHT_CATEGORY.getValue(), Config.IMAGE_WIDTH_CATEGORY.getValue(), Config.CHANNELS.getValue());
        INDArray input = loader.asMatrix(image).div(255.0f).permute(new int[]{1,2,0});
        INDArray  test =  Nd4j.zeros(1, Config.IMAGE_HEIGHT_CATEGORY.getValue(), Config.IMAGE_WIDTH_CATEGORY.getValue(), Config.CHANNELS.getValue()).add(0).add(input);
        INDArray result =  model.output(test);
        return indexMaxValue(result);
    }

    public int  predictionForColor(BufferedImage image) throws IOException, UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String clothesClassification = new ClassPathResource(
                "/static/color.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(clothesClassification );
        ImageLoader loader = new ImageLoader(Config.IMAGE_HEIGHT_COLOR.getValue(), Config.IMAGE_WIDTH_COLOR.getValue(), Config.CHANNELS.getValue());
        INDArray input = loader.asMatrix(image).div(255.0f).permute(new int[]{1,2,0});
        INDArray  test =  Nd4j.zeros(1, Config.IMAGE_HEIGHT_COLOR.getValue(), Config.IMAGE_WIDTH_COLOR.getValue(), Config.CHANNELS.getValue()).add(0).add(input);
        INDArray result =  model.output(test);
        return indexMaxValue(result);
    }
    public int indexMaxValue(INDArray result){
        int max = 0;
        for (int i = 1; i < result.columns(); i++){
            if (result.getDouble(max) < result.getDouble(i)) max = i;
        }
        return max;
    }


}
