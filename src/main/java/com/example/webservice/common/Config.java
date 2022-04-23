package com.example.webservice.common;

public enum Config {
    IMAGE_WIDTH_CATEGORY(200),
    IMAGE_HEIGHT_CATEGORY(200),
    IMAGE_WIDTH_COLOR(200),
    IMAGE_HEIGHT_COLOR(200),
    CHANNELS(3);

    private final int value;

    Config(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
