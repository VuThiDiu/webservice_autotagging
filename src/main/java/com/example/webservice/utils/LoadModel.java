package com.example.webservice.utils;

import com.example.webservice.common.Config;
import org.datavec.image.loader.ImageLoader;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.KerasModel;
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
        int predictionForCategory = predictionCategory(image);
        int predictionForColor = predictionColor(image);
        return new Image(tagCategory.get(predictionForCategory), tagColor.get(predictionForColor));

    }

    public int predictionCategory(BufferedImage image) throws IOException,
            UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String clothesClassification = new ClassPathResource(
                "/static/category.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(clothesClassification);
        ImageLoader loader = new ImageLoader(Config.IMAGE_HEIGHT_CATEGORY.getValue(),
                                            Config.IMAGE_WIDTH_CATEGORY.getValue(),
                                            Config.CHANNELS.getValue());
        INDArray input = loader.asMatrix(image).div(255.0f).permute(new int[]{1, 2, 0});
        INDArray loadImage = Nd4j.zeros(1,
                                    Config.IMAGE_HEIGHT_CATEGORY.getValue(),
                                    Config.IMAGE_WIDTH_CATEGORY.getValue(),
                                    Config.CHANNELS.getValue()).add(0).add(input);
        INDArray result = model.output(loadImage);
//        return indexMaxValue(result);
        return 2;
    }

    public int predictionColor(BufferedImage image) throws IOException,
            UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String clothesClassification = new ClassPathResource(
                "/static/color_7.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(clothesClassification);

        ImageLoader loader = new ImageLoader(Config.IMAGE_HEIGHT_COLOR.getValue(),
                                            Config.IMAGE_WIDTH_COLOR.getValue(),
                                            Config.CHANNELS.getValue());
//        // chuyển thành đạng 3 200 200
        INDArray input = loader.asMatrix(image).div(255.0f).permute(new int[]{1,2,0});
        int [][] test = getMatrixOfImage(image);
        // chuyển về dangj inoyut 200 200 3
//        INDArray loadImage = Nd4j.zeros(1,
//                                    Config.IMAGE_HEIGHT_COLOR.getValue(),
//                                    Config.IMAGE_WIDTH_COLOR.getValue(),
//                                    Config.CHANNELS.getValue()).add(input);
//        INDArray result = model.output(loadImage);
//        INDArray result = model.output(loadImage);
//        model.fit(loadImage, result);
        int[] result = model.predict(input.reshape(1,200,200,3));

        return result[0];
    }

    public int[][] getMatrixOfImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth(null);
        int height = bufferedImage.getHeight(null);
        int[][] pixels = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = bufferedImage.getRGB(i, j);
            }
        }

        return pixels;
    }
    public int indexMaxValue(int[] result) {
        int max = 0;
        for (int i = 1; i < result.length; i++) {
            if (result[max] < result[i]) max = i;
        }
        return max;
    }


}
