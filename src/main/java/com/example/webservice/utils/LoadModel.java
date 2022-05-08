package com.example.webservice.utils;

import com.example.webservice.common.Config;
import org.datavec.image.loader.ImageLoader;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.exceptions.InvalidKerasConfigurationException;
import org.deeplearning4j.nn.modelimport.keras.exceptions.UnsupportedKerasConfigurationException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.common.io.ClassPathResource;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.List;

import com.example.webservice.model.Image;

import static org.nd4j.linalg.factory.Nd4j.argMax;


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
        return indexMaxValue(result);

    }

    public int indexMaxValue(INDArray result){
        int max = 0;
        for (int i = 1; i < result.columns(); i++){
            if (result.getDouble(max) < result.getDouble(i)) max = i;
        }
        return max;
    }
    public int predictionColor(BufferedImage image) throws IOException,
            UnsupportedKerasConfigurationException, InvalidKerasConfigurationException {
        String clothesClassification = new ClassPathResource(
                "/static/color_7.h5").getFile().getPath();
        MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(clothesClassification);
        image = resizeImage(image, 200,200);
        INDArray input = loadImage(image).div(255.0f);
        return model.predict(input.reshape(1,200,200,3))[0];
    }

    public INDArray loadImage(BufferedImage image){
        int height = image.getHeight();
        int width = image.getWidth();
        int bands = image.getSampleModel().getNumBands();
        int[] shape = new int[]{height, width, bands};
        INDArray ret2 = Nd4j.create(new int[]{1, height*width*bands});
        long index = 0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++ ){
                Color mycolor = new Color(image.getRGB(i, j));
                ret2.putScalar(index, mycolor.getRed());
                ret2.putScalar(index + 1, mycolor.getGreen());
                ret2.putScalar(index + 2, mycolor.getBlue());
                index += 3;
            }
        }
        return ret2.reshape(shape);
    }
    // resize image
    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }


}
